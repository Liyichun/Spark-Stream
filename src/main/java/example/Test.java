package example;

/**
 * Created by Cynric on 8/11/16.
 */
class A {
    public A(String str) {

    }
}

public class Test {
    static{
        int x=5;
    }
    static int x,y;
    public static void main(String[] args) {
        Object o = new Object() {
            public boolean equals(Object obj) {
                return true;
            }
        };
        System.out.println(o.equals("Fred"));
    }
}
