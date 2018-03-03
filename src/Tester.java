import Jama.*;

public class Tester {
    
    public static final int MTXDIM = 10;
    public static final int RANK = 4;
    public static boolean PRINTMATRICES = true;
    
    public static void main(String[] args) { 
        Receiver r = new Receiver(MTXDIM, RANK);
        Sender s = new Sender(MTXDIM);
        PublicKey pubKey = r.createKeyPair();
        printPublicKey(pubKey);
        s.setKey(pubKey);
        EncryptedMessage msg = s.encryptMessage("please work or ill die");
        msg.getE().print(0, 0);
        msg.getEncryptedMatrix().print(0, 0);
        String praying = r.read(msg);
        System.out.println(praying);
    }
    
    public static String printPublicKey(PublicKey pubKey) {
        if (PRINTMATRICES) {
            printMatrix("A", pubKey.getA());
            printMatrix("B", pubKey.getB());
            printMatrix("G", pubKey.getG());
        }
        return null;
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
