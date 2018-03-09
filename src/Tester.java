import java.util.Arrays;
import java.util.Scanner;

import Jama.*;

public class Tester {
    
    public static final int MTXDIM = 10;
    public static final int RANK = 4;
    public static boolean PRINTMATRICES = true;
    
    public static void main(String[] args) { 
        Receiver r = new Receiver(MTXDIM, RANK);
        Sender s = new Sender(MTXDIM);
        PublicKey pubKey = r.createKeyPair();
        //printPublicKey(pubKey);
        s.setKey(pubKey);
//        System.out.print("Enter a string to encrypt: ");
//        EncryptedMessage msg = s.encryptMessage(new Scanner(System.in).nextLine());
//        System.out.println("K Matrix:");
//        s.getK().print(0, 0);
//        System.out.println("E Matrix:");
//        msg.getE().print(0, 0);
//        System.out.println("Encrypted Message Matrix:");
//        msg.getEncryptedMatrix().print(0, 0);
//        System.out.println("Encrypted Message:");
//        System.out.println(Common.matrixToMessage(msg.getEncryptedMatrix()));
//        String praying = r.read(msg);
//        Common.modMatrix(pubKey.getModulus(), r.decodar.times(s.getK())).print(0, 0);
//        System.out.println(praying);
        pubKey.getA().print(0, 0);
        Common.printMatrixLinear(pubKey.getA(), MTXDIM, "A");
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
