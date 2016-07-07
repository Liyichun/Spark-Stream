import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Cynric on 6/27/16.
 */
public class TestBroad {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);

        LinkedList<Integer> inputData = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            inputData.add(i);
        }
        Broadcast<Queue<Integer>> bc = sc.broadcast(inputData);
        JavaRDD<Integer> numbers = sc.parallelize(inputData);

        Util.log("Size before iteration", bc.getValue().size());

//        while (true) {
//            if (bc.getValue().isEmpty()) {
//                break;
//            }
//            bc.getValue().poll();
//            bc.getValue().poll();
////            bc.getValue().add(1);
//            Util.log("Size in iteration", bc.getValue().size());
//        }

        while (!bc.getValue().isEmpty()) {
            // 可以同时增减
            numbers.foreach(i -> {
                if (i % 2 == 0) {
                    bc.getValue().remove(i);
                } else {
                    bc.getValue().add(i * 2);
                }
            });
        }
        Util.log("Size after iteration", bc.getValue().size());
    }
}
