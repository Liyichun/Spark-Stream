package analysis.pre;

import antlr.Container;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import pds.Configuration;
import pds.TransRule;
import pds.Transition;
import util.Util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Cynric on 6/29/16.
 * 将trans分散到RDD中, delta 和 rel 都放在广播变量里,
 * 外层遍历trans,内层遍历delta和rel,
 * 终止条件是trans大小为0
 * 但是问题在于,trans互相独立,他们计算得到的结果无法及时共享
 */
public class SplitTransApp {
    public static void main(String[] args) {

        Container container = Util.parseInputFile("example/paper.pds");

        SparkConf conf = new SparkConf().setAppName("SplitTransApp").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<TransRule> delta = sc.parallelize(container.ruleSet);

        List<Transition> startTrans = Transition.getStartTrans(container.startConf);
        Broadcast<List<Transition>> bcTrans = sc.broadcast(new Vector<>(startTrans));

        Broadcast<Map<Transition, Object>> bcRel = sc.broadcast(new ConcurrentHashMap<Transition, Object>());
        Broadcast<Map<TransRule, Object>> bcDelta = sc.broadcast(new ConcurrentHashMap<TransRule, Object>());
        Accumulator<Integer> iterTime = sc.accumulator(0);

        delta.foreach(transRule -> {
            // find all <p, r> -> <p', e>   line2
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
            bcDelta.getValue().put(transRule, new Object());
        });

//        util.Util.log("Size of bcTrans", bcTrans.getValue().size());

        JavaRDD<Transition> trans = sc.parallelize(bcTrans.getValue());
        List<Transition> output;
        do {
            trans = trans.flatMap((Transition t) -> {
                String q = t.getStartState();
                String gamma = t.getAlphabet();
                String q_prime = t.getFinalState();

                List<Transition> flatMapRet = new ArrayList<>();

                if (!bcRel.getValue().containsKey(t)) { // line 5
                    if (bcRel.getValue() == null) {
                        Util.log("bcRel is NULL");
                    }
                    bcRel.getValue().put(t, new Object()); // line 6
                }

                for (TransRule transRule : bcDelta.getValue().keySet()) { // line 7 & line 9: traverse delta
                    Configuration endConf = transRule.getEndConf();
                    List<String> stackElements = endConf.getStackElements();

                    if (stackElements.size() == 1) { // line 7: check the size of stacks, we only accept size 1
                        if (q.equals(endConf.getState()) && // line 7: check belonging relationship
                                gamma.equals(stackElements.get(0))) {

                            flatMapRet.add(new Transition( // line 8
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

                            bcDelta.getValue().put(new TransRule( // line 10
                                    transRule.getStartConf(),
                                    new Configuration(q_prime, list)
                            ), new Object());

                            for (Transition transition : bcRel.getValue().keySet()) { // line 11: traverse Rel
                                if (transition.equals(q_prime, gamma2)) { // line 11: check belonging relationship
                                    flatMapRet.add(new Transition(
                                            transRule.getStartConf().getState(),
                                            transRule.getStartConf().getStackElements().get(0),
                                            transition.getFinalState()));

                                }
                            }
                        }
                    }
                }
//                synchronized (util.Util.class) {
//                    util.Util.logStart();
//                    util.Util.log("Input RDD", t.toString());
//                    util.Util.log("Output RDD", flatMapRet);
//                    util.Util.logEnd();
//                }
                return flatMapRet;
            }).distinct();

            output = trans.collect();

            Util.logStart();
            System.out.println("Iter " + iterTime.value() + " over ");
            Util.log("Size of Trans", output.size());
            Util.log("Content of Trans", output);
            Util.log("Size of Rel", bcRel.getValue().keySet().size());
            Util.log("Content of Rel", bcRel.getValue().keySet());
            Util.log("Size of Delta", bcDelta.getValue().keySet().size());
            Util.log("Content of Delta", bcDelta.getValue().keySet());
            Util.logEnd();

            iterTime.add(1);
            if (iterTime.value() == 2) {
                break;
            }

//            if (iterTime.value() % 50 == 0) {
//                util.Util.logStart();
//                util.Util.log("Size of Trans", output.size());
//                util.Util.log("Content of Trans", output);
//                util.Util.logEnd();
//
//                if (iterTime.value() == 200) {
//                    break;
//                }
//            }
        } while (output.size() > 0);

        Util.logStart();
        Util.log("Total iter times", iterTime.value());
        Util.log("Size of result", bcRel.getValue().keySet().size());
        Util.log("Content of result", bcRel.getValue().keySet());
        Util.logEnd();

    }

}
