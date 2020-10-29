package tool;
public class Calc {
    /**
     * 2个字异或
     * @param a 第一个字（4字节）
     * @param b 第二个字（4字节）
     * @return
     */
    public static int[] Xor(int[] a,int[] b){
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = a[i]^b[i];
        }
        return result;
    }

    /**
     * 两个4*4字节矩阵异或
     * @param a
     * @param b
     * @return
     */
    public static int[][] Xor(int[][] a,int[][] b){
        int[][] result = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = a[i][j]^b[i][j];
            }
        }
        return result;
    }

    /**
     * 两个矩阵乘法
     * @param a
     * @param b
     * @return
     */
    public static int[][] Mult(int[][] a,int[][] b){
        int[][] result = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = Mult(a[i][0],b[0][j])^Mult(a[i][1],b[1][j])^Mult(a[i][2],b[2][j])^Mult(a[i][3],b[3][j]);
            }
        }
        return result;
    }

    private static int Mult(int a,int b){
        return GF.GetContrary((GF.LogTable_03[a/16][a%16] + GF.LogTable_03[b/16][b%16])%255);
    }

    /**
     * 循环左移move个字节 行移位
     * @param a
     * @param move
     * @return
     */
    public static int[] Shift(int[] a,int move){
        //a:0x86,0xbd,0xf4,0x45
        //86bdf445
        //01234567
        move *= 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(String.format("%02X", a[i]));
        }
        String from = sb.toString();
        String first = from.substring(0,move);
        String second = from.substring(move);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        from = reChange(from);
        int[] result = new int[a.length];
        for(int i = 0;i<a.length;i++){
            result[i] = Integer.parseInt(from.substring(i*2,i*2+2),16);
        }
        return result;
    }
    /**
     * 反转字符串
     * @param from
     * @return
     */
    public static String reChange(String from){
        char[] froms = from.toCharArray();
        int length = froms.length;
        for (int i = 0; i < length/2; i++){
            char temp = froms[i];
            froms[i] = froms[length - 1 -i];
            froms[length - 1 -i] = temp;
        }
        return String.valueOf(froms);
    }

    public static void main(String[] args){
        int[] a = {0x86,0xbd,0xf4,0x45};
        a = Shift(a,1);
        for (int i = 0; i < 4; i++) {
            System.out.printf(String.format("%02X ", a[i]));
        }
        //60
        //result[1][1] = 01*E0 + 02*B4 + 03*52 + 01*ae
        System.out.println();
        System.out.printf("Mult(0x01,0xe0) = %02x" , Mult(0x01,0xe0));
        System.out.println();
        System.out.printf("Mult(0x02,0xb4) = %02x" , Mult(0x02,0xb4));
        System.out.println();
        System.out.printf("Mult(0x03,0x52) = %02x" , Mult(0x03,0x52));
        System.out.println();
        System.out.printf("Mult(0x01,0xae) = %02x" , Mult(0x01,0xae));
        System.out.println();
        System.out.println(String.format("%02x ",Mult(0x01,0xe0)^Mult(0x02,0xb4)^Mult(0x03,0x52)^Mult(0x01,0xae)));
    }
}
