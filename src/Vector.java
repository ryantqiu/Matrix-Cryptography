
public class Vector extends Matrix{

    public Vector(double[][] matrix) {
        super(matrix);
        if (matrix[0].length != 0) {
            throw new IllegalArgumentException("Not a Vector");
        }
    } 
}
