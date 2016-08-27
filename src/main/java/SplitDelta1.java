import antlr.Container;
import io.netty.util.internal.ConcurrentSet;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.broadcast.Broadcast;
import pds.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Cynric on 5/17/16.
 * 将Delta分散到RDD中, trans和rel放在广播变量中,
 * 按照伪代码的思路,每次从trans中取出一条记录,进行计算。
 * 外层遍历trans,内层遍历delta的RDD
 * 终止条件是trans的size变为0
 */
public class SplitDelta1 {

    public static void main(String[] args) {

        Container container = Util.parseInputFile("example/paper.pds");

        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<TransRule> delta = sc.parallelize(container.ruleSet);

        Configuration startConfg = container.startConf;
        List<Transition> startTrans = Transition.getStartTrans(startConfg);

        Broadcast<Queue<Transition>> bcTrans = sc.broadcast(new ConcurrentLinkedQueue<>(startTrans));
        Broadcast<Set<Transition>> bcRel = sc.broadcast(new ConcurrentSet<>());


        // find all <p, r> -> <p', e>  line2
        delta.foreach(transRule -> {
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
        });

        Util.log("The size of trans after line2", bcTrans.getValue().size());

        while (!bcTrans.getValue().isEmpty()) {   // line 3
            Util.log("The size of trans", bcTrans.getValue().size());
            Transition t = bcTrans.getValue().poll(); // line 4
            String q = t.getStartState();
            String gamma = t.getAlphabet();
            String q_prime = t.getFinalState();

            if (!bcRel.getValue().contains(t)) { // line 5
                bcRel.getValue().add(t); // line 6

                delta.flatMap(transRule -> {
                    List<TransRule> flatMapRet = new ArrayList<TransRule>();

                    Configuration endConf = transRule.getEndConf();
                    List<String> stackElements = endConf.getStackElements();

                    if (stackElements.size() == 1) { // line 7: check the size of stacks, we only accept size 1
                        if (q.equals(endConf.getState()) && // line 7: check belonging relationship
                                gamma.equals(stackElements.get(0))) {

                            bcTrans.getValue().add(new Transition( // line 8
                                    transRule.getStartConf().getState(),
                                    transRule.getStartConf().getStackElements().get(0),
                                    q_prime));

                        }
                    } else if (stackElements.size() == 2) { // line 9: check the size of stacks
                        String gamma2 = stackElements.get(1);

                        if (q.equals(endConf.getState()) && // line 9: check belonging relationship
                                gamma.equals(endConf.getStackElements().get(0))) {

                            List<String> list = new ArrayList<>(1);
                            list.add(gamma2);

                            flatMapRet.add(new TransRule( // line 10
                                    transRule.getStartConf(),
                                    new Configuration(q_prime, list)
                            ));

                            for (Transition transition : bcRel.getValue()) { // line 11: traverse rel
                                if (transition.equals(q_prime, gamma2)) { // line 11: check belonging relationship
                                    bcTrans.getValue().add(new Transition(
                                            transRule.getStartConf().getState(),
                                            transRule.getStartConf().getStackElements().get(0),
                                            transition.getFinalState()));

                                }
                            }
                        }
                    }
                    return flatMapRet;
                });
            }
        }

        Util.logStart();
//        Util.log("Total iter times", iterTime.value());
        Util.log("Size of result", bcRel.getValue().size());
        Util.log("Content of result", bcRel.getValue());
        Util.logEnd();
    }
}
