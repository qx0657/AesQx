package tool;

public class Print {
    public static void print(int[][] a){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(String.format("%02X ", a[i][j]));
            }
            System.out.println();
        }
    }
    public static void print(int[] a){
        for (int i = 0; i < 4; i++) {
            System.out.print(String.format("%02x ",a[i]));
        }
        System.out.println();
    }
}
