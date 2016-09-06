package util;

/**
 * Created by Cynric on 9/6/16.
 */
public class Cantor {
    public static int cal(int k1, int k2) {
        int ret = (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(cal(1, 3));
        System.out.println(cal(2, 2));
        System.out.println(cal(2, 3));
        System.out.println(cal(3, 1));
        System.out.println(cal(1, 4));
        System.out.println(cal(3, 2));
        System.out.println(cal(-15, 16));
        System.out.println(cal(10, 21));
    }

}
