
public class Matrix {
    private double[][] matrix;
    private int n;
    private int m;
    private int[][] reducedEchelonForm;
    
    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.n = matrix.length;
        this.m = matrix[0].length;
    }
    
    public Matrix reduce (Matrix matrix) {
        
    }
    
    public boolean isReduced() {
        for (int i = 0; i < n; i++) {
            
        }
    }
    
    private boolean isPivot(int row, int col) {
        for (int i = 0; i < n; i++) {
            if (i != row && (matrix[i][col] != 0)) {
                return false;
            }
        }
        return true;
    }
    
    public Matrix multiply(Matrix other) {
        if (this.m != other.n) {
            throw new IllegalArgumentException("Incompatible matrix dimensions");
        }
        double[][] retDimensions = new double[this.n][other.m];
        Matrix retMatrix = new Matrix(retDimensions);
        for (int i = 0; i < this.n; i++) { // aRow
            for (int j = 0; j < other.m; j++) { // bColumn
                for (int k = 0; k < this.m; k++) { // aColumn
                    retMatrix.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return retMatrix;
    }
    
    public Matrix linearTransform(Vector other) {
        Matrix ret = new Matrix(this.multiply(other).matrix);
        return ret;
    }
    
    public int getN() {
        return this.n;
    }
    
    public int getM() {
        return this.m;
    }
    
    public double[][] getMatrix() {
        return this.matrix;
    }
}