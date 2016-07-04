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

        List<Integer> inputData = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            inputData.add(i);
        }
        JavaRDD<Integer> numbers = sc.parallelize(inputData);
        Broadcast<Queue<Integer>> bc = sc.broadcast(new LinkedList<Integer>());

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

        // 可以同时增减
        numbers.foreach(i -> {
            if (i % 2 == 0) {
                bc.getValue().poll();
                bc.getValue().add(i);
            }
        });

        Util.log("Size after iteration", bc.getValue().size());


    }
}
