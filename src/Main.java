public class Main {

    public static void main(String[] args) {
        AesQx mAesQx = new AesQx("2b7e151628aed2a6abf7158809cf4f3c");
        String C = mAesQx.aesEncrypt("3243f6a8885a308d313198a2e0370734");
        System.out.println("密文：" + C);
    }
}
