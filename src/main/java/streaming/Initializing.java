package streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.spark_project.guava.collect.Lists;
import scala.Tuple2;
import util.SparkUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by yichli on 10/17/16.
 */
public class Initializing {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws InterruptedException {
        String appName = "liyichuntest";
        String master = "local[2]";
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master).set("spark.driver.allowMultipleContexts", "true");
        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000));
        ssc.checkpoint("."); //使用updateStateByKey()

        //也可以通过JavaStreamingContext来创建context
//        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());
//        JavaStreamingContext ssc2 = new JavaStreamingContext(sc, Durations.seconds(1));

        JavaReceiverInputDStream<String> lines = ssc.socketTextStream("local", 9999);

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(SPACE.split(s)).iterator();
            }
        });

        JavaPairDStream<String,Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        JavaPairDStream<String, Integer> word_count = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        word_count.print();
        ssc.start();
        ssc.awaitTermination();



    }
}
