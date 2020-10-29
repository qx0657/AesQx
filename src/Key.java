import tool.BHDConverter;
import tool.Calc;
import tool.Print;

/**
 * 封装密钥类
 * @author 胡光邦
 */
public class Key {
    private String keystr = "";

    public int[][][] key = new int[11][4][4];

    private int[][] Rcon = {
        {0x01,0x00,0x00,0x00},
        {0x02,0x00,0x00,0x00},
        {0x04,0x00,0x00,0x00},
        {0x08,0x00,0x00,0x00},
        {0x10,0x00,0x00,0x00},
        {0x20,0x00,0x00,0x00},
        {0x40,0x00,0x00,0x00},
        {0x80,0x00,0x00,0x00},
        {0x1b,0x00,0x00,0x00},
        {0x36,0x00,0x00,0x00}
    };

    public Key(String keystr){
        //128位16字节  字符串长度32

        if(keystr.length() != 32){
            System.out.println("密钥长度不正确");
            System.exit(-1);
        }
        System.out.println("密钥 = " + keystr);
        this.keystr = keystr;

        //初始化密钥矩阵
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //00 01 02 03--01 23 45 67
                //10 11 12 13--89
                temp[i][j] = Integer.parseInt( keystr.substring(i*8+j*2,i*8+j*2+2),16);
            }
        }
        //转置生成第一个密钥
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                key[0][i][j] = temp[j][i];
            }
        }
        //生成10个轮密钥
        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < 4; j++) {
                int[] result;
                //每组密钥的第一列（W[4*i]）的生成需要对上一组密钥的第四列（W[4*i]-1）进行特殊运算后与上一组密钥的第一列（W[4*i-4]）异或
                if(j==0){
                    //上一组密钥的第一列
                    int[] tt1 = getColumn(i-1,0);
                    //上一组密钥的第四列
                    int[] tt2 = getColumn(i-1,3);
                    result = Calc.Xor(g(tt2,i),tt1);
                }else{//如果不是密钥的第一列，即第2、3、4列，每列=它前面的那列^上一组密钥的当前列。（W[i] = W[i-1] ^ W[i-4]）
                    //获取当前该组密钥的前一列
                    int[] tt1 = getColumn(i,j-1);
                    //获取上一组密钥的当前列
                    int[] tt2 = getColumn(i-1,j);
                    result = Calc.Xor(tt1,tt2);
                }
                //把运算结果赋值给当前密钥的第j列
                SetColumn(i,j,result);
            }
        }

    }

    /**
     * 取第ki个密钥的第i列
     * @param ki
     * @param i
     * @return
     */
    private int[] getColumn(int ki,int i){
        int[] result = new int[4];
        for (int j = 0; j < 4; j++) {
            result[j] = key[ki][j][i];
        }
        return result;
    }

    /**
     * 设置第ki组密钥的第i列为v
     * @param ki
     * @param i
     * @param v
     */
    private void SetColumn(int ki,int i,int[] v){
        for (int j = 0; j < 4; j++) {
            key[ki][j][i] = v[j];
        }
    }
    /**
     * 左移1位
     * @param a 1个字（4字节）
     * @return
     */
    private int[] RotWord(int[] a){
        return Calc.Shift(a,1);
    }

    /**
     * 字节代换
     * @param a
     * @return
     */
    private int[] SubWord(int[] a){
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = S.s_box[a[i]/16][a[i]%16];
        }
        return result;
    }

    /**
     * 与Rcon[i-1]异或
     * @param a
     * @param i
     * @return
     */
    private int[] XorRcon(int[] a,int i){
        return Calc.Xor(a,Rcon[i-1]);
    }

    /**
     * 特殊操作
     * @param a
     * @param i
     * @return
     */
    private int[] g(int[] a,int i){
        a = RotWord(a);
        a = SubWord(a);
        a = XorRcon(a,i);
        return a;
    }

    public static void main(String[] args){
        Key mKey = new Key("2b7e151628aed2a6abf7158809cf4f3c");
        //测试查看扩展的11组密钥的正确性
        for (int i = 0; i < 11; i++) {
            System.out.println("key"+i+":");
            Print.print(mKey.key[i]);
        }
    }
}
