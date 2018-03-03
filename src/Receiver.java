import java.util.Random;

import Jama.Matrix;

public class Receiver {
    
    private int testModulus;
    private int mtxDim;
    private int rank;
    private Matrix privateKey;
    
    public Receiver(int testModulus, int mtxDim, int rank) {
        this.testModulus = testModulus;
        this.mtxDim = mtxDim;
        this.rank = rank;
    }
    
    public PublicKey createKeyPair() {
        Matrix a = generateRandomMatrix(rank);
        privateKey = generateRandomMatrix(rank);
        while (a.times(privateKey).getArray().equals(privateKey.times(a).getArray())) { // makes sure ac != ca
            privateKey = generateRandomMatrix(rank);
        }
        Matrix g = Common.modMatrix(testModulus, privateKey.times(privateKey)); // makes sure cg == gc
        Matrix b = Common.modMatrix(testModulus, (privateKey.times(a)).times(privateKey)); // b = cac
        return new PublicKey(a, b, g, testModulus);
    }
    
    public String read(EncryptedMessage message) {
        Matrix decoder = Common.modMatrix(testModulus, (privateKey.times(message.getE())).times(privateKey)).inverse(); // cec^-1
        Matrix decoded = decoder.times(message.getEncryptedMatrix());
        return Common.matrixToMessage(decoded);
    }  
    
    private Matrix generateRandomMatrix(int rank) {
        Random random = new Random();
        
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
            mtx[i][i] = random.nextInt(testModulus - 1) + 1;
        }
        return new Matrix(mtx).transpose();
    }
}

