import Jama.*;

public class Tester {
    public static void main(String[] args) { 
        Matrix test1 = new Matrix(new double[][]{{1,2,3},{4,7,9},{1,6,2}});
        Matrix test1Inv = test1.inverse();
        
        test1.print(0, 0);
        test1Inv.print(0, 0);
        test1.print(0, 0);
        Matrix id = test1.times(test1Inv);
        test1.times(id).print(0, 0);
    }
}
