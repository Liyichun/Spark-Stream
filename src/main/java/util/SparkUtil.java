package util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by Cynric on 9/11/16.
 */
public class SparkUtil {

    private static String masterStr = "local[8]";
//    private static  String masterStr = "spark://master";

    public static SparkConf getSparkConf(String appName) {
        SparkConf conf = new SparkConf();
        conf.setMaster(masterStr);
//                .setMaster("spark://master:7077");
        return conf;
    }

    public static SparkConf getSparkConf() {
        SparkConf conf = new SparkConf().setAppName("application");
//        conf.setMaster(masterStr);
//                .setMaster("spark://master:7077");
        return conf;
    }

    public static JavaSparkContext getJavaSparkContext() {
        return new JavaSparkContext(getSparkConf());
    }
}
