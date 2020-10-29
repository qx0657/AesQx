package tool;

/**
 * 进制转换
 * @author 胡光邦
 */
public class BHDConverter {
    /**
     * 16进制字符串转2进制字符串（每位16进制转换为4位）
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String b = "", temp;
        for (int i = 0; i < hexString.length(); i++) {
            temp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            b += temp.substring(temp.length() - 4);
        }
        return b;
    }
}
