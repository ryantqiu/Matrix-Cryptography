import Jama.*;
public class PublicKey {
    private Matrix a;
    private Matrix b;
    private Matrix g;
    private int modulus;
    
    public PublicKey(Matrix a, Matrix b, Matrix g, int modulus) {
        this.a = a;
        this.b = b;
        this.g = g;
        this.modulus = modulus;
    }
    
    public Matrix getA() {
        return a;
    }
    
    public Matrix getB() {
        return b;
    }
    
    public Matrix getG() {
        return g;
    }
    
    public int getModulus() {
        return modulus;
    }
}
