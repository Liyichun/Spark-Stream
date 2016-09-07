package util;


/**
 * Created by Cynric on 9/6/16.
 */
public class Cantor {
    public static int codePair(int k1, int k2) {
        int ret = (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
        return ret;
    }

    public static int[] dePair(int z) {
        double w = Math.floor((Math.sqrt(8 * z + 1) - 1) / 2);
        double t = (Math.pow(w, 2) + w) / 2;
        int y = (int) (z - t);
        int x = (int) (w - y);
        return new int[]{x, y};
    }


    public static void main(String[] args) {
        int z = codePair(1, 3);
        int[] ret = dePair(z);
        System.out.println(z);
        printPair(ret);

        z = codePair(2, 2);
        ret = dePair(z);
        System.out.println(z);
        printPair(ret);

        z = codePair(2, 3);
        ret = dePair(z);
        System.out.println(z);
        printPair(ret);

        z = codePair(3, 1);
        ret = dePair(z);
        System.out.println(z);
        printPair(ret);

        z = codePair(1, 4);
        ret = dePair(z);
        System.out.println(z);
        printPair(ret);

        z = codePair(3, 2);
        ret = dePair(z);
        System.out.println(z);
        printPair(ret);

    }

    public static void printPair(int[] a) {
        System.out.println(a[0] + ", " + a[1]);
    }

}
