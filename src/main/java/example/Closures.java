package example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by Cynric on 7/13/16.
 */
public class Closures {
    public static void main(String[] args) {

        int counter = 0;
        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> textFile = sc.textFile("example/README.md");

        // Wrong: Don't do this!!
//        textFile.foreach(x -> counter += x.length());

        System.out.println("Counter value: " + counter);
    }
}
