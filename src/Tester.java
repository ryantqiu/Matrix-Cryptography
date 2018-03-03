import Jama.*;

public class Tester {
    public static void main(String[] args) { 
        Receiver r = new Receiver(15, 5, 4);
        Sender s = new Sender();
        PublicKey pubKey = r.createKeyPair();
        s.setKey(pubKey);
        EncryptedMessage msg = s.encryptMessage("please work or ill die");
        msg.getE().print(0, 0);
        msg.getEncryptedMatrix().print(0, 0);
        String praying = r.read(msg);
        System.out.println(praying);
    }
}
