import java.util.Arrays;
import java.util.Random;
import Jama.Matrix;

public class Receiver {
    
    private int modulus;
    private int mtxDim;
    private int rank;
    private Matrix privateKey;
    private Random random;
    
    public Receiver(int mtxDim, int rank) {
        this.mtxDim = mtxDim;
        this.rank = rank;
        this.random = new Random();
        int[] primes = generatePrimes(10);
        System.err.println(Arrays.toString(primes));
        //modulus = primes[Math.abs(random.nextInt() % primes.length)] * primes[Math.abs(random.nextInt() % primes.length)];
        modulus = primes[random.nextInt(primes.length / 2) + primes.length / 2] * primes[random.nextInt(primes.length / 2) + primes.length / 2];
        //random.nextInt(primes.length / 2);
    }
    
    public PublicKey createKeyPair() {
        Matrix a = generateRandomMatrix(rank);
        while(gcd((int) a.det(), modulus) != 1) {
            a = generateRandomMatrix(rank);
        }
        privateKey = generateRandomMatrix(rank);
        while (a.times(privateKey).getArray().equals(privateKey.times(a).getArray()) || (privateKey.times(a)).times(privateKey).det() == 0 || gcd((int) privateKey.det(), modulus) != 1) { // makes sure ac != ca
            privateKey = generateRandomMatrix(rank);
        }
        Matrix b = Common.modMatrix(modulus, (privateKey.times(a)).times(privateKey)); // b = cac
        Matrix g = Common.modMatrix(modulus, privateKey.times(privateKey)); // makes sure cg == gc
        return new PublicKey(a, b, g, modulus);
    }
    
    public String read(EncryptedMessage message) {
        Matrix decoder = Common.modMatrix(modulus, (privateKey.times(message.getE())).times(privateKey)).inverse(); // cec^-1
        Matrix decoded = decoder.times(message.getEncryptedMatrix());
        return Common.matrixToMessage(decoded);
    }  
    
    private Matrix generateRandomMatrix(int rank) {        
        double[][] mtx = new double[mtxDim][mtxDim];
//        
//        for (int i = 0; i < rank; i++) {
//            for (int j = 0; j < mtxDim; j++) {
//                mtx[i][j] = random.nextInt(testModulus);
//            }
//        }
//        
//        for (int i = rank; i < mtxDim; i++) {
//            int randCol = random.nextInt(rank);
//            int randMultiplier = random.nextInt();
//            for (int j = 0; j < mtxDim; j++) {
//                mtx[i][j] = (mtx[randCol][j] * randMultiplier) % testModulus;
//            }
//        }
//        
//        return new Matrix(mtx).transpose();
        for (int i = 0; i < mtxDim; i++) {
            mtx[i][i] = 1 + random.nextInt(modulus - 1);
        }
        return new Matrix(mtx).transpose();
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
}

