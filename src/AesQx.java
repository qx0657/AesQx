import tool.Calc;
import tool.GF;
import tool.Print;

/**
 * @author 胡光邦
 */
public class AesQx {
    private Key mkey;
    private Message message;

    public AesQx(String keystr){
        //初始化密钥
        mkey = new Key(keystr);
        System.out.println("初始化密钥矩阵：");
        Print.print(mkey.key[0]);
    }

    /**
     * 初始化明文
     * @param msg
     */
    private void initMessage(String msg){
        message = new Message(msg);
        System.out.println("初始化明文矩阵：");
        Print.print(message.message_m);
    }

    /**
     * 加轮密钥
     * @param i 第i轮密钥
     */
    private void AddRoundKey(int i){
        message.message_m = Calc.Xor(message.message_m,mkey.key[i]);
        System.out.println("轮密钥加：");
        Print.print(message.message_m);
    }

    /**
     * 字节代换
     */
    private void ByteSub(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                message.message_m[i][j] = S.s_box[message.message_m[i][j]/16][message.message_m[i][j]%16];
            }
        }
        System.out.println("字节代换后：");
        Print.print(message.message_m);
    }

    /**
     * 行移位
     */
    private void ShinfRow(){
        for (int i = 1; i < 4; i++) {
            message.message_m[i] = Calc.Shift(message.message_m[i],i);
        }
        System.out.println("行移位后：");
        Print.print(message.message_m);
    }

    /**
     * 列混淆
     */
    private void MixColumns(){
        message.message_m = Calc.Mult(GF.routine_m, message.message_m);
        System.out.println("列混淆后：");
        Print.print(message.message_m);
    }

    /**
     * Aes加密
     * @param msg   明文
     * @return  密文
     */
    public String aesEncrypt(String msg){
        //初始化明文
        initMessage(msg);
        //初始轮密钥加
        AddRoundKey(0);
        //迭代10轮操作
        for (int i = 0; i < 10; i++) {
            System.out.println("第"+(i+1)+"轮:");
            //字节代换
            ByteSub();
            //行移位
            ShinfRow();
            if(i != 9){
                //列混淆
                MixColumns();
            }
            //轮密钥加
            AddRoundKey(i+1);
        }
        return message.getStr();
    }
}
