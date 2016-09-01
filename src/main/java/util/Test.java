package util;

import scala.Tuple2;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cynric on 9/1/16.
 */
public class Test {
    public static void testTuple() {
        Tuple2 t1 = new Tuple2(3, 4);
        Tuple2 t2 = new Tuple2(3, 4);
        Tuple2 t3 = new Tuple2(2, 5);

        Set<Tuple2> set = new HashSet<>();
        set.add(t1);
        set.add(t2);

        System.out.println(t1 == t2);
        System.out.println(t1.equals(t2));
        System.out.println(set.size());
    }

    public static void main(String[] args) {
        testTuple();
    }
}
