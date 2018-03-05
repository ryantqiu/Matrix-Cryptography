import Jama.*;

public class Common {
    public static Matrix messageToMatrix(String msg, int mtxDim) {
        if(msg == null) {
            throw new IllegalArgumentException("Message can not be null");
        }
        int matrixWidth = ((msg.length() - 1) / mtxDim) + 1;
        double[][] msgMtx = new double[mtxDim][matrixWidth];
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
    
    public static String matrixToMessage(Matrix matrix) {
        if(matrix == null) {
            throw new IllegalArgumentException("Matrix can not be null");
        }
        double[][] matrixArray = matrix.getArray();
        String result = "";
        for(int i = 0; i < matrixArray.length; i++) {
            for(int j = 0; j < matrixArray[0].length; j++) {
                result = result + (char) (matrixArray[i][j] % 127);
            }
        }
        return result;
    }
    
    public static Matrix modMatrix(int mod, Matrix toMod) {
        if(mod == 0) {
            throw new IllegalArgumentException("Mod must be greater than 0");
        }
        double[][] toModArr = toMod.getArray();
        for (int i = 0; i < toModArr.length; i++) {
            for (int j = 0; j < toModArr[0].length; j++) {
                toModArr[i][j] %= mod;
            }
        }
        return new Matrix(toModArr);
    }
}
