package example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.network.util.TransportFrameDecoder;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.avro.TypeEnum.b;

/**
 * Created by Cynric on 6/29/16.
 */
public class TestRdd {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 从内存集合中构造RDD
        JavaRDD<Integer> integers = sc.parallelize(Arrays.asList(1, 2));
        JavaRDD<Integer> integers2 = sc.parallelize(Arrays.asList(1, 3, 5, 7));
        JavaRDD<String> strings = sc.parallelize(Arrays.asList("a b", "c d", "e f", "g h"));


        // 该测试表明,一个rdd的transformation内部无法再调用其他的transformation。
//        List output =
//                integers.flatMap(i -> {
//                    List<Integer> ret = new ArrayList<>();
//                    integers2.map(i2 -> {
//                        if (i == i2) {
//                            ret.add(i2);
//                        }
//                        return i2;
//                    });
//
//                    return ret;
//                }).collect();
//
//        System.out.println(output.size());
//        System.out.println(output.toString());

        JavaRDD<int[]> intArray = sc.parallelize(Arrays.asList(
                new int[]{1, 2, 3, 4},
                new int[]{1, 2, 3, 4}
        ));
//
        JavaPairRDD<Tuple2, int[]> pairRDD = intArray.mapToPair(a -> {
            Tuple2 sig = new Tuple2(a[2], a[3]);
            return new Tuple2<>(sig, a);
        });

        pairRDD = pairRDD.flatMapToPair(tuple -> {
            System.out.println(tuple.toString());
            List<Tuple2<Tuple2, int[]>> ret = new ArrayList<>();

            return ret.iterator();
        });

//        for (int i = 0; i < 5; i ++ ) {
//            integers = integers.flatMap(i1 -> {
//                List<Integer> ret = new ArrayList();
//                ret.add(i1);
//                if (i1 == 2) {
//                    ret.add(i1);
//                }
//                return ret;
//            });
//            integers.count();
//            System.out.println(integers.count());
//        }


    }
}
