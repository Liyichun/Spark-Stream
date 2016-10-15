package analyzer;

import model.pds.simple.Transition;
import scala.Tuple2;
import scala.Tuple3;
import util.Cantor;

import java.util.*;

/**
 * Created by Cynric on 12/10/2016.
 */
public class PdsAnalyzer {
    public static Tuple2<Integer, Integer> getTransTuple(int[] trans) {
        return new Tuple2<>(Cantor.codePair(trans[0], trans[1]), trans[2]);
    }

    public static Tuple2<Integer, Integer> getTransTuple(int x, int y, int z) {
        return new Tuple2<>(Cantor.codePair(x, y), z);
    }

    public static Map<Integer, Set<Integer>> transTupleListToMap(List<Tuple2<Integer, Integer>> list) {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (Tuple2<Integer, Integer> tuple : list) {
            Integer key = tuple._1;
            Integer value = tuple._2;

            if (map.containsKey(key)) {
                map.get(key).add(value);
            } else {
                Set<Integer> haha = new HashSet<>();
                haha.add(value);
                map.put(key, haha);
            }
        }
        return map;
    }

    public void printTransTuple3(Tuple3<Integer, Integer, Integer> tuple) {
        System.out.println(Transition.toString(tuple));
    }

    public void printTranSet(Collection<Tuple3<Integer, Integer, Integer>> collection) {
        for (Tuple3<Integer, Integer, Integer> tuple : collection) {
            printTransTuple3(tuple);
        }
    }

}
