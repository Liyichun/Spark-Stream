package example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;
import util.SparkUtil;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Cynric on 7/13/16.
 */
public class WordCount {

    public static void main(String[] args) {

        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());

        JavaRDD<String> textFile = sc.textFile("example/README.md");
        JavaRDD<Integer> lineLength = textFile.map(s -> s.length());
        int totalLength = lineLength.reduce((a, b) -> a + b);
        System.out.println("length of file: " + totalLength);

        JavaRDD<String> words = textFile.flatMap((String s) -> Arrays.asList(s.split(" ")).iterator());

        JavaPairRDD<String, Integer> pairs = words.mapToPair((String s) -> new Tuple2<>(s, 1));

        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((Integer a, Integer b) -> a + b);

        System.out.println(counts.collect().toString());

    }
}
