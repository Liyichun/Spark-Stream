import pds.TransRule;
import pds.Transition;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cynric on 7/7/16.
 */
public class TestSet {


    public static void main(String[] args) {
        Transition t1 = new Transition("a", "a", "a");
        Transition t2 = new Transition("a", "a", "a");

        Set<Transition> set = new HashSet<>();
        set.add(t1);
        set.add(t2);

        System.out.println(set.size());
    }
}
