import java.util.Scanner;

import Jama.Matrix;

public class Simulator {
    
    public static final boolean PRINTMATRICES = false;
    public static boolean isReceiver;
    private static Receiver r;
    private static Sender s;
    
    public static void main(String[] args) {
        System.out.print("Are you the sender(1) or receiver(2)?: ");
        String type = new Scanner(System.in).nextLine();
        isReceiver = type.equals("2") ? true : false;
        System.out.print("Enter a matrix dimension: ");
        int mtxDim = new Scanner(System.in).nextInt();
        if (isReceiver) {
            receiverTask(mtxDim);
        } else {
            senderTask(mtxDim);
        }
    }
    
    private static void receiverTask(int mtxDim) {
        System.out.println("Press 1 to generate key pair");
        System.out.println("Press 2 to decrypt message");
        String msg = new Scanner(System.in).nextLine();
        if (msg.equals("1")) {
            printKeyPairs(mtxDim);
        } else {
            System.out.println("");
            System.out.println("Enter your encrypted message below:");
            Scanner input = new Scanner(System.in);
            Matrix msgMtx = Common.readMatrixLinear(input.nextLine(),mtxDim);
            Matrix e = Common.readMatrixLinear(input.nextLine(),mtxDim);
            msgMtx.print(0, 0);
            e.print(0, 0);
            EncryptedMessage incomingMsg = new EncryptedMessage(msgMtx, e);
            System.out.println(r.read(incomingMsg));
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
        Common.printMatrixLinear(key.getA(), mtxDim);
        Common.printMatrixLinear(key.getB(), mtxDim);
        Common.printMatrixLinear(key.getG(), mtxDim);
        System.out.println(key.getModulus());
        main(null);
    }
    
    private static void senderTask(int mtxDim) {
        System.out.println("Enter the public key below:");
        Scanner input = new Scanner(System.in);
        Matrix a = Common.readMatrixLinear(input.nextLine(),mtxDim);
        Matrix b = Common.readMatrixLinear(input.nextLine(),mtxDim);
        Matrix g = Common.readMatrixLinear(input.nextLine(),mtxDim);
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
        Common.printMatrixLinear(msg.getEncryptedMatrix(), mtxDim);
        Common.printMatrixLinear(msg.getE(), mtxDim);
        main(null);
    }
}
