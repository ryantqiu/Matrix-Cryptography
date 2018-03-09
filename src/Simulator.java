import java.util.Scanner;

import Jama.Matrix;

public class Simulator {
    
    public static final boolean PRINTMATRICES = true;
    
    
    public static boolean isReceiver;
    private static Receiver r;
    private static Sender s;
    
    public static void main(String[] args) {
        System.out.print("Are you the sender(1) or receiver(2)?: ");
        String type = new Scanner(System.in).nextLine();
        isReceiver = type.equals("2") ? true : false;
        if (isReceiver) {
            receiverTask();
        } else {
            senderTask();
        }
    }
    
    // RECEIVER'S METHODS
    
    private static void receiverTask() {
        System.out.println("Press 1 to generate key pair");
        System.out.println("Press 2 to decrypt message");
        String msg = new Scanner(System.in).nextLine();
        if (msg.equals("1")) {
            System.out.print("Enter a matrix dimension: ");
            int mtxDim = new Scanner(System.in).nextInt();
            printKeyPairs(mtxDim);
        } else {
            decryptMessage();
        }
    }

    private static void printKeyPairs(int mtxDim) {
        r = new Receiver(mtxDim);
        PublicKey publicKey = r.createKeyPair();
        if (PRINTMATRICES) {
            System.out.println("A:");
            publicKey.getA().print(0, 0);
            System.out.println("B:");
            publicKey.getB().print(0, 0);
            System.out.println("G:");
            publicKey.getG().print(0, 0);
            System.out.println("n:" + publicKey.getModulus());
            System.out.println();
        }
        printPublicKey(publicKey, mtxDim);
    }
    
    private static void printPublicKey(PublicKey key, int mtxDim) {
        System.out.println("Public Key:");
        System.out.println();
        Common.printMatrixLinear(key.getA());
        Common.printMatrixLinear(key.getB());
        Common.printMatrixLinear(key.getG());
        System.out.println(key.getModulus());
        System.out.println();
        System.out.println();
        System.out.println("Press 1 when you have the key saved");
        Scanner wait = new Scanner(System.in);
        if (wait.nextLine() != null) {
            clearConsole();
            main(null);
        }
        main(null);
    }
    
    private static void decryptMessage() {
        System.out.println("");
        System.out.println("Enter your encrypted message below:");
        Scanner input = new Scanner(System.in);
        String encrpytedMsg = input.nextLine();
        String eMtx = input.nextLine();
        int mtxDim = (int) Math.sqrt((double) eMtx.split(",").length);
        Matrix msgMtx = Common.readMatrixLinear(encrpytedMsg, mtxDim, encrpytedMsg.split(",").length / mtxDim);
        Matrix e = Common.readMatrixLinear(eMtx,mtxDim, mtxDim); 
        EncryptedMessage incomingMsg = new EncryptedMessage(msgMtx, e);
        String decrpytedMsg = r.read(incomingMsg);
        if (PRINTMATRICES) {
            System.out.println("K:");
            r.decodar.print(0, 0);
        }
        System.out.println();
        System.out.println("Decrypted Message:");
        System.out.println(decrpytedMsg);
    }
    
    // SENDER'S METHODS
    
    private static void senderTask() {
        System.out.println("Enter the public key below:");
        Scanner input = new Scanner(System.in);
        String dimCalc = input.nextLine();
        int mtxDim = (int) Math.sqrt((double) dimCalc.split(",").length);
        Matrix a = Common.readMatrixLinear(dimCalc, mtxDim, mtxDim);
        Matrix b = Common.readMatrixLinear(input.nextLine(),mtxDim, mtxDim);
        Matrix g = Common.readMatrixLinear(input.nextLine(),mtxDim, mtxDim);
        int n = Integer.parseInt(input.nextLine());
        System.out.println("Enter your message below:");
        String message = new Scanner(System.in).nextLine();
        s = new Sender(mtxDim);
        PublicKey key = new PublicKey(a, b, g, n);
        s.setKey(key);
        EncryptedMessage msgToSend = s.encryptMessage(message);
        printEncryptedMessage(msgToSend, mtxDim);
    }
    
    private static void printEncryptedMessage(EncryptedMessage msg, int mtxDim) {
        if (PRINTMATRICES) {
            System.out.println("D:");
            s.getD().print(0, 0);
            System.out.println("K:");
            s.getK().print(0, 0);
            System.out.println("E:");
            msg.getE().print(0, 0);
            System.out.println("M_e:");
            msg.getEncryptedMatrix().print(0, 0);
            System.out.println("Scrambled Message:");
            System.out.println();
            System.out.println(Common.matrixToMessage(msg.getEncryptedMatrix()));
        }
        System.out.println("Encrypted Message:");
        System.out.println();
        Common.printMatrixLinear(msg.getEncryptedMatrix());
        Common.printMatrixLinear(msg.getE());
        System.out.println();
        System.out.println();
        System.out.println("Press 1 when you have the message saved");
        Scanner wait = new Scanner(System.in);
        if (wait.nextLine() != null) {
            clearConsole();
            main(null);
        }
    }
    
    private static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
