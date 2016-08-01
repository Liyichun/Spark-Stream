import antlr.Container;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import pds.TransRule;
import pds.Transition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cynric on 7/8/16.
 */
public class SplitDeltaApp {

    public static void main(String[] args) {
        Container container = Util.parseInputFile("example/plot.pds");

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<TransRule> delta = sc.parallelize(container.ruleSet);

        Broadcast<List<Transition>> bcTrans = sc.broadcast(new LinkedList<>());

        delta.foreach(transRule -> {
            // find all <p, r> -> <p', e>   line2
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
        });


    }

}
