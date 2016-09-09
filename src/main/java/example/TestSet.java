package example;

import model.pds.*;

import java.util.*;

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


        List<String> l1 = Arrays.asList("a", "b", "c");
        List<String> l2 = Arrays.asList("a", "b", "c");
        System.out.println("l1 == l2: " + l1.equals(l2));

        Set<List<String>> listSet = new HashSet<>();
        listSet.add(l1);
        listSet.add(l2);
        System.out.println("list set size: " + listSet.size());


        Configuration conf1 = new Configuration("s", l1);
        Configuration conf2 = new Configuration("s", l2);
        System.out.println("conf1 == conf2: " + conf1.equals(conf2));
        Set<Configuration> confSet = new HashSet<>();
        confSet.add(conf1);
        confSet.add(conf2);
        System.out.println("conf set size: " + confSet.size());


        TransRule tr1 = new TransRule(conf1, conf2);
        TransRule tr2 = new TransRule(new Configuration("s", Arrays.asList("a", "b", "c")),
                new Configuration("s", Arrays.asList("a", "b", "c")));
        System.out.println("tr1 == tr2: " + tr1.equals(tr2));

        Set<TransRule> trSet = new HashSet<>();
        trSet.add(tr1);
        trSet.add(tr2);
        System.out.println("tr set size: " + trSet.size());


    }
}
