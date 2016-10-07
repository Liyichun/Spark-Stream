package test;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;
import util.Cantor;
import util.SparkUtil;

import java.util.*;

/**
 * Created by Cynric on 9/14/16.
 */
public class TestJoin {
    public static void main(String[] args) {


        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());

        JavaRDD<int[]> delta = sc.parallelize(
                Arrays.asList(
                        new int[]{1, 2, 3, 4, 5},
                        new int[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4, 6}
                ));


        JavaRDD<int[]> trans = sc.parallelize(
                Arrays.asList(
                        new int[]{3, 4, 10},
                        new int[]{3, 4, 11},
                        new int[]{3, 4, 12}));


        JavaPairRDD<Integer, int[]> deltaPair = delta.mapToPair(
                array -> {
                    int sig = Cantor.codePair(array[2], array[3]);
                    return new Tuple2<>(sig, array);
                });

        JavaPairRDD<Integer, int[]> transPair = trans.mapToPair(array -> {
            int sig = Cantor.codePair(array[0], array[1]);
            return new Tuple2<>(sig, array);
        });


        Date startDate = new Date();

        for (int i = 0; i < 100; i++) {

            JavaRDD<Tuple2<int[], int[]>> joinResult = deltaPair.join(transPair).values().distinct();


            JavaPairRDD<Integer, int[]> newDeltaPair = joinResult.flatMapToPair(tuple -> {
                int[] a1 = tuple._1;
                int[] a2 = tuple._2;
                List<Tuple2<Integer, int[]>> ret = new ArrayList<>();

                if (a1.length == 5) {
                    Tuple2<Integer, int[]> newTuple = new Tuple2<>(
                            Cantor.codePair(a1[0], a1[1]),
                            new int[]{a1[0], a1[1], a2[2], a1[4]}
                    );
                    ret.add(newTuple);
                }
                return ret.iterator();
            });

            deltaPair = deltaPair.union(newDeltaPair);
        }
        Date endDate = new Date();
        System.out.println("reduce-side join: " + (endDate.getTime() - startDate.getTime()));


        /***************/

        Map<Integer, List<Tuple2<Integer, Integer>>> m = deltaArrayToMap(deltaPair.collect());
        Broadcast<Map<Integer, List<Tuple2<Integer, Integer>>>> delta_bc = sc.broadcast(m);

        transPair.map(tuple -> {
            Integer sig = tuple._1;
            int[] value = tuple._2;
        });





    }


    public static Map deltaArrayToMap(List<Tuple2<Integer, int[]>> list) {
        Map<Integer, List<Tuple2<Integer, Integer>>> map = new HashMap<>();

        for (Tuple2<Integer, int[]> tuple : list) {
            Integer key = tuple._1;
            Tuple2<Integer, Integer> value = new Tuple2<>(tuple._2[0], tuple._2[1]);

            if (map.containsKey(key)) {
                map.get(key).add(value);
            } else {
                List<Tuple2<Integer, Integer>> haha = new ArrayList<>();
                haha.add(value);
                map.put(key, haha);
            }
        }
        return map;
    }
}
