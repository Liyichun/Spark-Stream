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

 /*       for (int i = 0; i < 10; i++) {

            JavaRDD<Tuple2<int[], int[]>> joinResult = deltaPair.join(transPair).values().distinct();


//            JavaPairRDD<Integer, int[]> newDeltaPair = joinResult.flatMapToPair(tuple -> {
//                int[] a1 = tuple._1;
//                int[] a2 = tuple._2;
//                List<Tuple2<Integer, int[]>> ret = new ArrayList<>();
//
//                if (a1.length == 5) {
//                    Tuple2<Integer, int[]> newTuple = new Tuple2<>(
//                            Cantor.codePair(a1[0], a1[1]),
//                            new int[]{a1[0], a1[1], a2[2], a1[4]}
//                    );
//                    ret.add(newTuple);
//                }
//                return ret.iterator();
//            });
            JavaPairRDD<Integer, int[]> newTransPair =
                    joinResult.flatMapToPair(tuple -> {
                        int[] rule = tuple._1;
                        int[] tran = tuple._2;
                        List<Tuple2<Integer, int[]>> ret = new ArrayList<>();

                        if (rule.length == 4) {
                            Tuple2<Integer, int[]> newTrans = new Tuple2<Integer, int[]>(
                                    Cantor.codePair(rule[0], rule[1]),
                                    new int[]{rule[0], rule[1], tran[2]}
                            );
                            ret.add(newTrans);
                        }
                        return ret.iterator();
                    }).distinct();

            transPair = transPair.union(newTransPair).distinct();

        }
        */
        Date endDate = new Date();
        System.out.println("reduce-side join: " + (endDate.getTime() - startDate.getTime()));


        /***************/


        Map<Integer, Set<Integer>> m = transArrayToMap(transPair.collect());
        Broadcast<Map<Integer, Set<Integer>>> trans_bc = sc.broadcast(m);

        startDate = new Date();
        for (int i = 0; i < 10; i++) {
            System.out.println("trans map size: " + trans_bc.getValue().size());

            JavaPairRDD<Integer, Integer> newTransPair =
                    deltaPair.flatMapToPair(tuple -> {
                        Map<Integer, Set<Integer>> map = trans_bc.getValue();
                        Set<Tuple2<Integer, Integer>> ret = new HashSet<>();

                        Integer sig = tuple._1;
                        if (map.containsKey(sig)) {
                            int[] rule = tuple._2;
                            if (rule.length == 4) {
                                for (Integer toState : map.get(sig)) {
                                    int newTranSig = Cantor.codePair(rule[0], rule[1]);
                                    Tuple2<Integer, Integer> newTrans = new Tuple2<>(
                                            newTranSig,
                                            toState
                                    );
                                    if (!map.containsKey(newTranSig)
                                            && !map.get(newTranSig).contains(toState)) {
                                        ret.add(newTrans);
                                    }
                                }
                            }
                        }
                        return ret.iterator();
                    });

            Map<Integer, Set<Integer>> m2 = transArraToMap(newTransPair.collect());
            for (Integer key : m2.keySet()) {
                Map<Integer, Set<Integer>> map = trans_bc.getValue();
                if (map.containsKey(key)) {
                    map.get(key).addAll(m2.get(key));
                } else {
                    map.put(key, m2.get(key));
                }
            }
        }
        endDate = new Date();
//        System.out.println("map-side join: " + (endDate.getTime() - startDate.getTime()));

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

    public static Map transArraToMap(List<Tuple2<Integer, Integer>> list) {
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


    public static Map transArrayToMap(List<Tuple2<Integer, int[]>> list) {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (Tuple2<Integer, int[]> tuple : list) {
            Integer key = tuple._1;
            Integer value = Integer.valueOf(tuple._2[2]);

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


}
