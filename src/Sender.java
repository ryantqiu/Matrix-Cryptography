import Jama.Matrix;

public class Sender {
    private PublicKey publickey;
    private Matrix d;
    private Matrix k;
    private Matrix e;
    
    public void setKey(PublicKey publickey) {
        this.publickey = publickey;
        this.k = null;
        this.e = null;
    }
    
    private void generateD() {
        if(publickey.getG() == null) {
            throw new NullPointerException("Public key is missing Matrix 'G'");
        }
        Matrix g = publickey.getG();
        Matrix d = Common.modMatrix(publickey.getModulus(), g.times(g));
        this.d = d;
    }
    
    private void generateK() {
        if(publickey.getB() == null) {
            throw new NullPointerException("Public key is missing Matrix 'K'");
        }
        Matrix b = publickey.getB();
        Matrix bd = Common.modMatrix(publickey.getModulus(), b.times(d));
        Matrix k = Common.modMatrix(publickey.getModulus(), d.times(bd));
        this.k = k;
    }
    
    private void generateE() {
        if(publickey.getA() == null) {
            throw new NullPointerException("Public key is missing Matrix 'E'");
        }
        Matrix a = publickey.getA();
        Matrix ad = Common.modMatrix(publickey.getModulus(), a.times(d));
        Matrix e = Common.modMatrix(publickey.getModulus(), d.times(ad));
        this.e = e;
    }
    
    public EncryptedMessage encryptMessage(String message) {
        if(message == null || message.length() == 0) {
            throw new IllegalArgumentException("A message with nonzero length is required");
        }
        if(publickey == null) {
            throw new NullPointerException("A public key must be added in order to encrypt a message.");
        }
        if(d == null) {
            generateD();
        }
        if(e == null) {
            generateE();
        }
        if(k == null) {
            generateK();
        }
        Matrix messageMatrix = Common.messageToMatrix(message);
        Matrix encryptedMatrix = k.times(messageMatrix);
        return new EncryptedMessage(encryptedMatrix, e);
    }   
}
