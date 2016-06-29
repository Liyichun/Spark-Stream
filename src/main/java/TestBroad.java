import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Cynric on 6/27/16.
 */
public class TestBroad {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Broadcast<Queue<Integer>> bc = sc.broadcast(new LinkedList<Integer>());

        for (int i = 0; i < 5; i++) {
            bc.getValue().add(i);
        }
        Util.log("Size before iteration", bc.getValue().size());

        while (true) {
            if (bc.getValue().isEmpty()) {
                break;
            }
            bc.getValue().poll();
            bc.getValue().poll();
//            bc.getValue().add(1);
            Util.log("Size in iteration", bc.getValue().size());
        }

        Util.log(bc.getValue().size());


    }
}
