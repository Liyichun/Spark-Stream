import antlr.Container;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import pds.Configuration;
import pds.TransRule;
import pds.Transition;

import java.util.*;

/**
 * Created by Cynric on 6/29/16.
 */
public class SplitTransApp {
    public static void main(String[] args) {

        Container container = Util.parseInputFile("example/plot.pds");

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<TransRule> delta = sc.parallelize(container.ruleSet);

//        Broadcast<Set<TransRule>> bcDelta = sc.broadcast(new HashSet<>());
//        Broadcast<Set<TransRule>> bcDelta_prime = sc.broadcast(new HashSet<>());

        Broadcast<List<Transition>> bcTrans = sc.broadcast(new ArrayList<>());
        Broadcast<Set<TransRule>> bcDelta = sc.broadcast(new HashSet<>());
        Broadcast<Set<Transition>> bcRel = sc.broadcast(new HashSet<>());


        delta.foreach(transRule -> {
            // find all <p, r> -> <p', e>   line2 of pseudocode
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
        });

        Util.log("Size of bcTrans", bcTrans.getValue().size());

        JavaRDD<Transition> trans = sc.parallelize(bcTrans.getValue());


    }

}
