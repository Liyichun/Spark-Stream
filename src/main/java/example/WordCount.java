package example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Created by Cynric on 7/13/16.
 */
public class WordCount {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("WordCount").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = sc.textFile("example/README.md");
        JavaRDD<Integer> lineLength = textFile.map(s -> s.length());
        int totalLength = lineLength.reduce((a, b) -> a + b);
        System.out.println("length of file: " + totalLength);

        JavaRDD<String> words = textFile.flatMap((String s) -> Arrays.asList(s.split(" ")));

        JavaPairRDD<String, Integer> pairs = words.mapToPair((String s) -> new Tuple2<>(s, 1));

        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((Integer a, Integer b) -> a + b);

        System.out.println(counts.collect().toString());

    }
}
