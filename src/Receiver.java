import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import Jama.Matrix;

public class Receiver {
    
    private int modulus;
    private int mtxDim;
    private int rank;
    private Matrix privateKey;
    private Random random;
    public Matrix decodar;
    
    public Receiver(int mtxDim, int rank) {
        this.mtxDim = mtxDim;
        this.rank = rank;
        this.random = new Random();
        int[] primes = generatePrimes(1000);
        //modulus = primes[Math.abs(random.nextInt() % primes.length)] * primes[Math.abs(random.nextInt() % primes.length)];
        modulus = primes[random.nextInt(primes.length / 2) + primes.length / 2];
        System.out.print(modulus);
    }
    
    public PublicKey createKeyPair() {
        Matrix a = generateRandomMatrix(rank);
        while(gcd((int) a.det(), modulus) != 1) {
            a = generateRandomMatrix(rank);
        }
        privateKey = generateRandomMatrix(rank);
        while (Common.modMatrix(modulus, a.times(privateKey)).equals(Common.modMatrix(modulus, privateKey.times(a))) || Common.modMatrix(modulus, (privateKey.times(a)).times(privateKey)).det() == 0 || gcd((int) privateKey.det(), modulus) != 1) { // makes sure ac != ca
            privateKey = generateRandomMatrix(rank);
        }
        Matrix b = Common.modMatrix(modulus, (privateKey.times(a)).times(privateKey)); // b = cac
        Matrix g = null;
        for (int i = 0; i < 2 * (random.nextInt(10) + 1); i++) {
            g = Common.modMatrix(modulus, privateKey.times(privateKey)); // cakes sure cg = gc
        }
        return new PublicKey(a, b, g, modulus);
    }
    
    public String read(EncryptedMessage message) {
        Matrix decoder = Common.modMatrix(modulus, (privateKey.times(message.getE())).times(privateKey)); // cec
        //decoder.print(0, 0);
        decoder = invert(decoder);
        decodar = decoder;
        Matrix decoded = Common.modMatrix(modulus, decoder.times(message.getEncryptedMatrix()));
        return Common.matrixToMessage(decoded);
    }
    
    private Matrix generateRandomMatrix(int rank) {        
        double[][] mtx = new double[mtxDim][mtxDim];
        for (int i = 0; i < mtxDim; i++) {
            mtx[i][i] = 2 + random.nextInt(modulus - 2);
        }
        Matrix m = new Matrix(mtx);
        if(m.det() == 0) {
            return generateRandomMatrix(rank);
        }
        return m;
    }
    
    private static int gcd(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
    
    public Matrix invert(Matrix mtx) {
        double[][] old = mtx.getArray();
        double[][] inverse = new double[mtxDim][mtxDim];
        for (int i = 0; i < mtxDim; i++) {
            for (int j = 0; j < mtxDim; j++) {
                if (old[i][j] == 0) {
                    inverse[i][j] = 0;
                } else {
                    inverse[i][j] = modInverse(old[i][j], modulus);
                }
            }
        }
        return new Matrix(inverse);
    }
    
    public static double modInverse(double a, int mod) {
        a = a % mod;
        for (int x = 1; x < mod; x++) {
           if ((a * x) % mod == 1) {
              return x;
           }
        }
        return 1;
    }

    public int[] generatePrimes(int maxValue) {
        if (maxValue >= 2) {
            int s = maxValue + 1;
            boolean[] f = new boolean[s];
            int i;
            for (i = 0; i < s; i++)
                f[i] = true;
            f[0] = f[1] = false;
            int j;
            for (i = 2; i < Math.sqrt(s) + 1; i++) {
                if (f[i])
                {
                    for (j = 2 * i; j < s; j += i)
                        f[j] = false;
                }
            }
            int count = 0;
            for (i = 0; i < s; i++) {
                if (f[i])
                    count++;
            }
            int[] primes = new int[count];
            for (i = 0, j = 0; i < s; i++) {
                if (f[i])
                    primes[j++] = i;
            }
            return primes;
        } else
            return new int[0];
    } 
     
//    public double modInverse(double a, double m)
//    {
//        if (gcd((int) a, (int) m) == 1) {
//            throw new IllegalArgumentException();
//        }
//        EgcdResult result = egcd(a, m);
//        if (result.xIsNegative) {
//            return m - result.x;
//        }
//        return result.x;
//    }
//     
//    public EgcdResult egcd(double a, double m)
//    {
//        int gcd = gcd((int) a, (int) m);
//        if (m == 0) {
//            return new EgcdResult(gcd, 1, 0, false);
//        }
//        EgcdResult result = egcd(m, a % m);
//        return new EgcdResult(gcd, result.y, result.x + ((a / m) * result.y), !result.xIsNegative);
//    }
}

