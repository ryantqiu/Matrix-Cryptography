import java.util.Arrays;
import java.util.Random;
import Jama.Matrix;

public class Receiver {
    
    private int testModulus;
    private int mtxDim;
    private int rank;
    private Matrix privateKey;
    private Random random;
    
    public Receiver(int mtxDim, int rank) {
        this.mtxDim = mtxDim;
        this.rank = rank;
        this.random = new Random();
        int[] primes = generatePrimes(100);
//        System.err.println(Arrays.toString(primes));
        testModulus = primes[Math.abs(random.nextInt() % primes.length)] * primes[Math.abs(random.nextInt() % primes.length)];
    }
    
    public PublicKey createKeyPair() {
        Matrix a = generateRandomMatrix(rank);
        privateKey = generateRandomMatrix(rank);
        while (a.times(privateKey).getArray().equals(privateKey.times(a).getArray()) || (privateKey.times(a)).times(privateKey).det() == 0) { // makes sure ac != ca
            privateKey = generateRandomMatrix(rank);
        }
        Matrix b = Common.modMatrix(testModulus, (privateKey.times(a)).times(privateKey)); // b = cac
        Matrix g = Common.modMatrix(testModulus, privateKey.times(privateKey)); // makes sure cg == gc
        return new PublicKey(a, b, g, testModulus);
    }
    
    public String read(EncryptedMessage message) {
        Matrix decoder = Common.modMatrix(testModulus, (privateKey.times(message.getE())).times(privateKey)).inverse(); // cec^-1
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
            mtx[i][i] = 1 + random.nextInt(testModulus - 1);
        }
        return new Matrix(mtx).transpose();
    }
    
    public int[] generatePrimes(int maxValue) {
        if (maxValue >= 2) {// the only valid case
            // declarations
            int s = maxValue + 1; // size of array
            boolean[] f = new boolean[s];
            int i;
            // initialize array to true.
            for (i = 0; i < s; i++)
                f[i] = true;
            // get rid of known non-primes
            f[0] = f[1] = false;
            // sieve
            int j;
            for (i = 2; i < Math.sqrt(s) + 1; i++) {
                if (f[i]) // if i is uncrossed, cross its multiples.
                {
                    for (j = 2 * i; j < s; j += i)
                        f[j] = false; // multiple is not prime
                }
            }
            // how many primes are there?
            int count = 0;
            for (i = 0; i < s; i++) {
                if (f[i])
                    count++; // bump count.
            }
            int[] primes = new int[count];
            // move the primes into the result
            for (i = 0, j = 0; i < s; i++) {
                if (f[i]) // if prime
                    primes[j++] = i;
            }
            return primes; // return the primes
        } else
            // maxValue < 2
            return new int[0]; // return null array if bad input.
    }
}

