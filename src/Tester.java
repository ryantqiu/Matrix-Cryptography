import java.util.Arrays;

import Jama.*;

public class Tester {
    
    public static final int MTXDIM = 6;
    public static final int RANK = 4;
    public static boolean PRINTMATRICES = true;
    
    public static void main(String[] args) { 
        Receiver r = new Receiver(MTXDIM, RANK);
        Sender s = new Sender(MTXDIM);
        PublicKey pubKey = r.createKeyPair();
        //printPublicKey(pubKey);
        s.setKey(pubKey);
        EncryptedMessage msg = s.encryptMessage("please work or ill die");
        String praying = r.read(msg);
        System.out.println(praying);
    }
    
    public static double[] printPublicKey(PublicKey pubKey) {
        if (PRINTMATRICES) {
            printMatrix("A", pubKey.getA());
            printMatrix("B", pubKey.getB());
            printMatrix("G", pubKey.getG());
        }
        double[] result = new double[3 * MTXDIM * MTXDIM + 1];
        int curr = 0;
        double[][] a = pubKey.getA().getArray();
        double[][] b = pubKey.getB().getArray();
        double[][] g = pubKey.getG().getArray();
        
        for (int i = 0; i < MTXDIM; i++) {
            for (int j = 0; j < MTXDIM; j++) {
                result[curr] = a[i][j];
                curr++;
            }
        }
        for (int i = 0; i < MTXDIM; i++) {
            for (int j = 0; j < MTXDIM; j++) {
                result[curr] = b[i][j];
                curr++;
            }
        }
        for (int i = 0; i < MTXDIM; i++) {
            for (int j = 0; j < MTXDIM; j++) {
                result[curr] = g[i][j];
                curr++;
            }
        }
        result[result.length - 1] = pubKey.getModulus();
        System.out.println(Arrays.toString(result));
        return result;
    }
    
    public static void printMatrix(String label, Matrix mtx) {
        System.out.println(label + ":");
        mtx.print(0, 0);
        for (int i = 0; i < MTXDIM; i++) {
            System.out.print("_");
        }
        System.out.println();
    }
}
