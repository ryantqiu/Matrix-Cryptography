import Jama.*;
public class Encryptor {
    
    public static void main(String[] args) {
        
    }
    
    private static Matrix messageToMatrix(String msg) {
        int mtxDim = (int) Math.ceil(Math.sqrt(msg.length()));
        double[][] msgMtx = new double[mtxDim][mtxDim];
        int stringPos = 0;
        for (int i = 0; i < msgMtx.length; i++) {
            for (int j = 0; j < msgMtx[0].length; j++) {
                if (stringPos >= msg.length()) {
                    break;
                } else {
                    msgMtx[i][j] = msg.charAt(stringPos);
                    stringPos++;
                }
            }
        }
        return new Matrix(msgMtx);
    }
}
