package analysis.pre;

import antlr.Container;
import io.netty.util.internal.ConcurrentSet;
import org.apache.spark.Accumulator;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.broadcast.Broadcast;
import pds.*;
import util.Util;

import java.util.*;

import static com.sun.tools.doclint.Entity.delta;

/**
 * Created by Cynric on 5/17/16.
 * 将Delta分散到RDD中, trans和rel放在广播变量中,
 * 在外层遍历Delat的RDD,在内层遍历trans的广播变量,将新的结果追加到trans中
 * 终止条件是trans的size不再增长
 */
public class SplitDelta2 {

    public static void main(String[] args) {

//        String inputFile = "plot_2";
        String inputFile = "Mpds110_2";
//        String inputFile = "plot";
//        String inputFile = "test";
        Container container = Util.parseInputFile("example/" + inputFile + ".pds");

        SparkConf conf = new SparkConf().setAppName("SplitDelta2").setMaster("local[200]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<TransRule> delta = sc.parallelize(container.ruleSet);

        Accumulator<Integer> lastSum = sc.accumulator(-1);
        Accumulator<Integer> currSum = sc.accumulator(0);
        Accumulator<Integer> iterTime = sc.accumulator(0);

        Configuration startConfg = container.startConf;
        List<Transition> startTrans = Transition.getStartTrans(startConfg);

        Set<Transition> trans = new ConcurrentSet<>();
        for (Transition t : startTrans) {
            trans.add(t);
        }
        Broadcast<Set<Transition>> bcTrans = sc.broadcast(trans);
        Broadcast<Set<Transition>> bcRel = sc.broadcast(new ConcurrentSet<>());


        // find all <p, r> -> <p', e>  line2
//        delta.foreach(transRule -> {
//            if (transRule.getEndConf().getStackElements().size() == 0) {
//                Transition transition = transRule.toTransition();
//                if (!transition.getFinalState().equals(container.startConf.getState())) {
//                    bcTrans.getValue().add(transition);
//                }
//            }
//        });

        Date startDate = new Date();

        while (!lastSum.value().equals(currSum.value())) {   // line 3
            iterTime.add(1);
//            util.Util.logStart();
//            util.Util.log("Iter time: ", iterTime.value());
//            util.Util.log("size of last", lastSum.value());
//            util.Util.log("size of current", currSum.value());
//            util.Util.logEnd();

            lastSum.setValue(currSum.value());

            delta = delta.flatMap(transRule -> {
//                System.out.println("enter flatmap: " + transRule);

                List<TransRule> flatMapRet = new ArrayList<>();

                Configuration endConf = transRule.getEndConf();
                List<String> stackElements = endConf.getStackElements();

                if (stackElements.size() == 0) {
                    Transition newTransition = transRule.toTransition();
                    for (Transition t : bcTrans.getValue()) {
                        if (newTransition.getFinalState().equals(t.getStartState())) {
                            bcTrans.getValue().add(newTransition);
                            currSum.add(1);
                        }
                    }
                }

                for (Transition t : bcTrans.getValue()) {
                    String q = t.getStartState();
                    String gamma = t.getAlphabet();
                    String q_prime = t.getFinalState();

//                    util.Util.logStart();
//                    System.out.println(t);
//                    System.out.println(endConf);
//                    util.Util.logEnd();
                    if (stackElements.size() == 1) { // line 7: check the size of stacks, we only accept size 1
                        if (q.equals(endConf.getState()) && // line 7: check belonging relationship
                                gamma.equals(stackElements.get(0))) {

                            Transition newTransition = new Transition( // line 8
                                    transRule.getStartConf().getState(),
                                    transRule.getStartConf().getStackElements().get(0),
                                    q_prime);

                            if (!bcTrans.getValue().contains(newTransition)) {
                                bcTrans.getValue().add(newTransition);
                                currSum.add(1);
                            }


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

                            for (Transition transition : bcTrans.getValue()) { // line 11: traverse rel
                                if (transition.equals(q_prime, gamma2)) { // line 11: check belonging relationship

                                    Transition newTransition = new Transition(
                                            transRule.getStartConf().getState(),
                                            transRule.getStartConf().getStackElements().get(0),
                                            transition.getFinalState());

                                    if (!bcTrans.getValue().contains(newTransition)) {
                                        bcTrans.getValue().add(newTransition);
                                        currSum.add(1);
                                    }
                                }
                            }
                        }
                    }
                }
                return flatMapRet;
            });
            delta.count();
        }

        Date endDate = new Date();
        Util.logStart();
        Util.log("Start time: ", startDate.getTime());
        Util.log("End time: ", endDate.getTime());
        Util.log("Duration: ", endDate.getTime() - startDate.getTime());
        Util.log("Total iter times", iterTime.value());
        Util.log("Size of result", bcTrans.getValue().size());
        Util.dumpToFile("output/" + inputFile + ".txt", bcTrans.getValue());
        Util.logEnd();
    }
}
