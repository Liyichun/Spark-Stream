package example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

import static org.apache.avro.TypeEnum.b;

/**
 * Created by Cynric on 7/13/16.
 */
public class Example1 {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 从文件中构造RDD
        JavaRDD<String> textFile = sc.textFile("example/README.md");

        // 从内存集合中构造RDD
        JavaRDD<Integer> integers = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 4, 3, 2, 1));
        JavaRDD<Integer> integers2 = sc.parallelize(Arrays.asList(1, 3, 5, 7));
        JavaRDD<String> strings = sc.parallelize(Arrays.asList("a b", "c d", "e f", "g h"));


        System.out.println("======map操作======");
        System.out.println(integers.map(x -> x + 1).collect().toString());
        System.out.println(integers.collect().toString());
        System.out.println("======map操作======");

        System.out.println("======filter操作======");
        System.out.println(integers.filter(x -> x > 4).collect().toString());
        System.out.println("======filter操作======");


        System.out.println("======flatMap操作======");
        System.out.println(strings.flatMap(s -> Arrays.asList(s.split(" "))).collect().toString());
        System.out.println("======flatMap操作======");


        System.out.println("======distinct去重======");
        System.out.println(integers.distinct().collect().toString());
        System.out.println("======distinct去重======");


        System.out.println("======union操作======");
        System.out.println(integers.union(integers2).collect().toString());
        System.out.println("======union操作======");


        System.out.println("======intersection操作======");
        System.out.println(integers.intersection(integers2).collect().toString());
        System.out.println("======intersection操作======");


        System.out.println("======subtract操作======");
        System.out.println(integers.subtract(integers2).collect().toString());
        System.out.println("======subtract操作======");


        System.out.println("======cartesian操作======");
        System.out.println(integers.cartesian(integers2).collect().toString());
        System.out.println("======cartesian操作======");


        System.out.println("======count操作======");
        System.out.println(integers.count());
        System.out.println("======count操作======");


        System.out.println("======countByValue操作======");
        System.out.println(integers.countByValue());
        System.out.println("======countByValue操作======");


        System.out.println("======reduce操作======");
        System.out.println(integers.reduce((a, b) -> a + b));
        System.out.println("======reduce操作======");

        JavaRDD<Integer> lineLengths = textFile.map(String::length);
        //JavaRDD<Integer> lineLengths = textFile.map(s -> s.length());
        int totalLength = lineLengths.reduce((a, b) -> a + b);
        System.out.println("length of file: " + totalLength);


        int sum = integers.reduce((a, b) -> a + b);
        System.out.println("sum: " + sum);

    }
}
