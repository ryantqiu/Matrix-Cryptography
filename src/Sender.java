import java.util.Random;

import Jama.Matrix;

public class Sender {
    private PublicKey publicKey;
    private Matrix d;
    private Matrix k;
    private Matrix e;
    private int mtxDim;
    private Random random;
    
    public Sender(int mtxDim){
        this.mtxDim = mtxDim;
        this.random = new Random();
    }
    
    public void setKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        this.k = null;
        this.e = null;
    }
    
    private void generateD() {
        if(publicKey.getG() == null) {
            throw new NullPointerException("Public key is missing Matrix 'G'");
        }
        Matrix g = publicKey.getG();
        Matrix d = null;
        for (int i = 0; i < 2 * (random.nextInt(10) + 1); i++) {
            d = Common.modMatrix(publicKey.getModulus(), g.times(g));
        }
        this.d = d;
    }
    
    private void generateK() {
        if(publicKey.getB() == null) {
            throw new NullPointerException("Public key is missing Matrix 'K'");
        }
        Matrix b = publicKey.getB();
        Matrix bd = Common.modMatrix(publicKey.getModulus(), b.times(d));
        Matrix dbd = Common.modMatrix(publicKey.getModulus(), d.times(bd));
        this.k = dbd;
    }
    
    private void generateE() {
        if(publicKey.getA() == null) {
            throw new NullPointerException("Public key is missing Matrix 'E'");
        }
        Matrix a = publicKey.getA();
        Matrix ad = Common.modMatrix(publicKey.getModulus(), a.times(d));
        Matrix dad = Common.modMatrix(publicKey.getModulus(), d.times(ad));
        this.e = dad;
    }
    
    public EncryptedMessage encryptMessage(String message) {
        if(message == null || message.length() == 0) {
            throw new IllegalArgumentException("A message with nonzero length is required");
        }
        if(publicKey == null) {
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
        Matrix messageMatrix = Common.messageToMatrix(message, mtxDim);
        //Matrix encryptedMatrix = Common.modMatrix(publicKey.getModulus(), k.times(messageMatrix));
        Matrix encryptedMatrix = k.times(messageMatrix);
        return new EncryptedMessage(encryptedMatrix, e);
    }
    
    public Matrix getD() {
        return this.d;
    }
    
    public Matrix getK() {
        return this.k;
    }
}
