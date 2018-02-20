import java.util.Random;

public class Tester {
    public static void main(String[] args) {
        Matrix one = fillMatrix(new Matrix(new double[5][3]));
        Matrix two = fillMatrix(new Matrix(new double[3][4]));
        
        printMatrix(one);
        printMatrix(two);
        printMatrix(one.multiply(two));
    }
    
    public static void printMatrix(Matrix mtx) {
        double[][] matrix = mtx.getMatrix();
        for (int i = 0; i < mtx.getN(); i++) {
            System.out.print("|");
            for (int j = 0; j < mtx.getM(); j++) {
                System.out.print(" " + matrix[i][j]);
            }
            System.out.println(" |");
        }
    }
    
    public static Matrix fillMatrix(Matrix mtx) {
        double[][] matrix = mtx.getMatrix();
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return new Matrix(matrix);
    }
}
