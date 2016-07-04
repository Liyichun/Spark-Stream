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
public class NewApp {
    public static void main(String[] args) {

        List<TransRule> ruleSet = Util.parseInputFile("example/plot.pds");

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<TransRule> delta = sc.parallelize(ruleSet);

//        Broadcast<Set<TransRule>> bcDelta = sc.broadcast(new HashSet<>());
//        Broadcast<Set<TransRule>> bcDelta_prime = sc.broadcast(new HashSet<>());

        Broadcast<Set<Transition>> bcTrans = sc.broadcast(new HashSet<>());
        Broadcast<Set<Transition>> bcRel = sc.broadcast(new HashSet<>());


        delta.foreach(transRule -> {
            // find all <p, r> -> <p', e>   line2 of pseudocode
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
        });

        delta.foreach(transRule -> {
            Configuration endConf = transRule.getEndConf();
            List<String> endStackElems = endConf.getStackElements();
            String q = endConf.getState();
            String gamma = endStackElems.get(0);

            if (endStackElems.size() == 1) {
                for (Transition t : bcTrans.getValue()) {
                    if (q.equals(t.getStartState()) && gamma.equals(t.getAlphabet())) {

                    }
                }

            } else if (endStackElems.size() == 2) {
                String gamma2 = endStackElems.get(1);


            }


        });


    }

}
