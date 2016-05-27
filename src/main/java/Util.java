/**
 * Created by Cynric on 5/26/16.
 */
public class Util {

    public static void log(String s) {
        System.out.println("****************************** program output start ******************************");
        System.out.println(s);
        System.out.println("****************************** program output end ******************************");

    }

    public static void log(int i) {
        log(String.valueOf(i));
    }

    public static void log(long i) {
        log(String.valueOf(i));
    }
}
