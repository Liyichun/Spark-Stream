package test;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import util.Cantor;
import util.SparkUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Cynric on 9/14/16.
 */
public class TestJoin {
    public static void main(String[] args) {


        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());

        JavaRDD<int[]> delta = sc.parallelize(Arrays.asList(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4}));

        JavaRDD<int[]> trans = sc.parallelize(Arrays.asList(new int[]{3, 4, 10}, new int[]{3, 4, 11}, new int[]{3, 4, 12}));

        JavaPairRDD<Integer, int[]> deltaPair = delta.mapToPair(array -> {
            int sig = Cantor.codePair(array[2], array[3]);
            return new Tuple2<Integer, int[]>(sig, array);
        });

        JavaPairRDD<Integer, int[]> transPair = trans.mapToPair(array -> {
            int sig = Cantor.codePair(array[0], array[1]);
            return new Tuple2<Integer, int[]>(sig, array);
        });

        JavaRDD<Tuple2<int[], int[]>> joinResult = deltaPair.join(transPair).values().distinct();


        JavaPairRDD<Integer, int[]> newDeltaPair = joinResult.flatMapToPair(tuple -> {
            int[] a1 = tuple._1;
            int[] a2 = tuple._2;
            List<Tuple2<Integer, int[]>> ret = new ArrayList<>();

            if (a1.length == 5) {
                Tuple2<Integer, int[]> newTuple = new Tuple2<Integer, int[]>(
                        Cantor.codePair(a1[0], a1[1]),
                        new int[]{a1[0], a1[1], a2[2], a1[4]}
                );
                ret.add(newTuple);
            }
            return ret.iterator();
        });

        deltaPair = deltaPair.union(newDeltaPair);


        List<int[]> singleArrayResult = deltaPair.values().collect();

        for (int[] a : singleArrayResult) {
            System.out.println(Arrays.toString(a));
        }


//        List<Tuple2<int[], int[]>> result = joinResult.collect();
//
//        for (Tuple2<int[], int[]> tuple : result) {
//            System.out.println(Arrays.toString(tuple._1) + ": " + Arrays.toString(tuple._2));
//        }

    }
}
