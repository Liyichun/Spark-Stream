package analyzer;

import scala.Tuple2;
import util.Cantor;

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

}
