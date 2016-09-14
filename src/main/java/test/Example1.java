package test;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.CollectionAccumulator;
import util.SparkUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Cynric on 7/13/16.
 */
public class Example1 {
    public static void main(String[] args) {

        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());

        // 从文件中构造RDD
        JavaRDD<String> textFile = sc.textFile("example/README.md");

        // 从内存集合中构造RDD
        JavaRDD<Integer> integers = sc.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> integers2 = sc.parallelize(Arrays.asList(1, 3, 5, 7));
        JavaRDD<String> strings = sc.parallelize(Arrays.asList("a b", "c d", "e f", "g h"));


        List<Integer> l = new ArrayList<>();
        Broadcast<List<Integer>> broadcast = sc.broadcast(l);

        CollectionAccumulator<Integer> ac = sc.sc().collectionAccumulator();

        for (int j = 0; j < 3; j++) {
            integers.map(i -> {
                System.out.println("i: " + i);
                System.out.println("accumulator: " + ac.value());
                System.out.println("broadcast: " + broadcast.value());
                ac.add(i);
                broadcast.getValue().add(i);
                return i;
            }).count();
//            System.out.println(ac.value());
        }

        System.out.println("accumulator: " + ac.value());
        System.out.println("broadcast: " + broadcast.value());


//        System.out.println("======map操作======");
//        System.out.println(integers.map(x -> x + 1).collect().toString());
//        System.out.println(integers.collect().toString());
//        System.out.println("======map操作======");
////
//        System.out.println("======filter操作======");
//        System.out.println(integers.filter(x -> x > 4).collect().toString());
//        System.out.println("======filter操作======");
//
//
//        System.out.println("======flatMap操作======");
//        System.out.println(strings.flatMap(s -> Arrays.asList(s.split(" ")).iterator()).collect().toString());
//        System.out.println(strings.collect().toString());
//        System.out.println("======flatMap操作======");


//        System.out.println("======distinct去重======");
//        System.out.println(integers.distinct().collect().toString());
//        System.out.println("======distinct去重======");
//
//
//        System.out.println("======union操作======");
//        System.out.println(integers.union(integers2).collect().toString());
//        System.out.println("======union操作======");
//
//
//        System.out.println("======intersection操作======");
//        System.out.println(integers.intersection(integers2).collect().toString());
//        System.out.println("======intersection操作======");
//
//
//        System.out.println("======subtract操作======");
//        System.out.println(integers.subtract(integers2).collect().toString());
//        System.out.println("======subtract操作======");
//
//
//        System.out.println("======cartesian操作======");
//        System.out.println(integers.cartesian(integers2).collect().toString());
//        System.out.println("======cartesian操作======");
//
//
//        System.out.println("======count操作======");
//        System.out.println(integers.count());
//        System.out.println("======count操作======");
//
//
//        System.out.println("======countByValue操作======");
//        System.out.println(integers.countByValue());
//        System.out.println("======countByValue操作======");
//
//
//        System.out.println("======reduce操作======");
//        System.out.println(integers.reduce((a, b) -> a + b));
//        System.out.println("======reduce操作======");
//
//        JavaRDD<Integer> lineLengths = textFile.map(String::length);
//        //JavaRDD<Integer> lineLengths = textFile.map(s -> s.length());
//        int totalLength = lineLengths.reduce((a, b) -> a + b);
//        System.out.println("length of file: " + totalLength);
//
//
//        int sum = integers.reduce((a, b) -> a + b);
//        System.out.println("sum: " + sum);

    }
}
