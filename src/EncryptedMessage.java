import Jama.Matrix;

public class EncryptedMessage {
    private Matrix encryptedMatrix;
    private Matrix e;
    
    public EncryptedMessage(Matrix encryptedMatrix, Matrix e) {
        if(encryptedMatrix == null || e == null) {
            throw new IllegalArgumentException("Matrices can not be null");
        }
        this.encryptedMatrix = encryptedMatrix;
        this.e = e;
    }
    
    public Matrix getE() {
        return e;
    }
    
    public Matrix getEncryptedMatrix() {
        return encryptedMatrix;
    }
}
