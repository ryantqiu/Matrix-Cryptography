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
        int[] primes = generatePrimes(10000);
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
        while (Common.modMatrix(modulus, a.times(privateKey)).getArray().equals(Common.modMatrix(modulus, privateKey.times(a)).getArray()) || Common.modMatrix(modulus, (privateKey.times(a)).times(privateKey)).det() == 0 || gcd((int) privateKey.det(), modulus) != 1) { // makes sure ac != ca
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
        return new Matrix(mtx);
    }
    
    public static int gcd(int p, int q) {
        if (q == 0) return p;
        if (p == 0) return q;

        // p and q even
        if ((p & 1) == 0 && (q & 1) == 0) return gcd(p >> 1, q >> 1) << 1;

        // p is even, q is odd
        else if ((p & 1) == 0) return gcd(p >> 1, q);

        // p is odd, q is even
        else if ((q & 1) == 0) return gcd(p, q >> 1);

        // p and q odd, p >= q
        else if (p >= q) return gcd((p-q) >> 1, q);

        // p and q odd, p < q
        else return gcd(p, (q-p) >> 1);
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
//        EgcdResult res = new EgcdResult(0, a, m);
//        res = gcdExtended(a, m, res);
//        if (res.gcd != 1) {
//            throw new IllegalArgumentException();
//        } else {
//            // m is added to handle negative x
//            double result = (res.x % m + m) % m;
//            return res.x;
//        }
//    }
//     
//    // C function for extended Euclidean Algorithm
//    public EgcdResult gcdExtended(double a, double b, EgcdResult res)
//    {
//        // Base Case
//        if (a == 0)
//        {
//            res.x = 0;
//            res.y = 1;
//            return res;
//        }
//     
//        EgcdResult newRes = new EgcdResult(a, 0, 0); // To store results of recursive call
//        newRes = gcdExtended(b%a, a, newRes);
//     
//        // Update x and y using results of recursive
//        // call
//        res.x = newRes.y - (b/a) * newRes.x;
//        res.y = newRes.x;
//     
//        return res;
//    }
}

