
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
            double[][] retDimensions = new double[this.n][other.m];
            Matrix retMatrix = new Matrix(retDimensions);
            for (int i = 0; i < other.m; i++) {
                
            }
        }
    }
    
    public Matrix linearTransform(Vector other) {
        Matrix ret = new Matrix(this.multiply(other).matrix);
        return ret;
    }
}