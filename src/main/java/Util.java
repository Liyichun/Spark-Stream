/**
 * Created by Cynric on 5/26/16.
 */
public class Util {

    public static void log(String content) {
        System.out.println("****************************** program output start ******************************");
        System.out.println("CONTENT: " + content);
        System.out.println("****************************** program output end ******************************");
    }

    public static void log(String title, String content) {
        System.out.println("****************************** program output start ******************************");
        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
        System.out.println("****************************** program output end ******************************");
    }


    public static void log(int i) {
        log(String.valueOf(i));
    }

    public static void log(long i) {
        log(String.valueOf(i));
    }

    public static void log(String title, int i) {
        log(title, String.valueOf(i));
    }

    public static void log(String title, long i) {
        log(title, String.valueOf(i));
    }
}
