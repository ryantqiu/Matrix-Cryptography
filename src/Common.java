import java.util.Arrays;


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
    
    public static void printMatrixLinear(Matrix mtx) {
        double[][] origMtx = mtx.getArray();
        int n = origMtx.length;
        int m = origMtx[0].length;
        double[] mtxPrint = new double[n * m];
        int pos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mtxPrint[pos] = origMtx[i][j];
                pos++;
            }
        }
        String toPrint = Arrays.toString(mtxPrint);
        System.out.println(toPrint);
    }
    
    public static Matrix readMatrixLinear(String mtx, int n, int m) {
        double[][] ret = new double[n][m];
        double[] temp = new double[n * m];
        int pos = 0;
        String[] strNums = mtx.substring(1, mtx.indexOf("]")).split(",");
        for (int i = 0; i < strNums.length; i++) {
            temp[i] = Double.parseDouble(strNums[i]);
        }
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
                ret[j][k] = temp[pos];
                pos++;
            }
        }
        return new Matrix(ret);
    }
}
