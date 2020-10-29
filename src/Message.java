/**
 * 封装明文类
 * @author 胡光邦
 */
public class Message {
    public int[][] message_m = new int[4][4];

    public Message(String msg){
        if(msg.length() < 32){
            System.out.println("请输入32长度明文（128位）");
            System.exit(-1);
        }
        System.out.println("明文 = " + msg);
        //初始化明文矩阵
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = Integer.parseInt( msg.substring(i*8+j*2,i*8+j*2+2),16);
            }
        }
        //转置
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                message_m[i][j] = temp[j][i];
            }
        }

    }
    public String getStr(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //按列导出
                sb.append(String.format("%02X", message_m[j][i]));
            }
        }
        return sb.toString();
    }
}
