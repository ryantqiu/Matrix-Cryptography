import Jama.Matrix;
public class Receiver {
    
    private int testModulus;
    private Matrix privateKey;
    
    private PublicKey createPublicKey() {
        
    }
    
    private String read(Message) {
        
    }
    
    
    private Matrix messageToMatrix(String msg) {
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
    
    private Matrix modArray(PublicKey pubKey, Matrix toMod) {
        double[][] toModArr = toMod.getArray();
        for (int i = 0; i < toModArr.length; i++) {
            for (int j = 0; j < toModArr[0].length; j++) {
                toModArr[i][j] %= pubKey.getModulus();
            }
        }
        return new Matrix(toModArr);
    }
    
    
}

