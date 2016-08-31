package analysis.pre;

import antlr.simple.*;
import io.netty.util.internal.ConcurrentSet;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import pds.simple.*;
import util.Util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Cynric on 5/17/16.
 * 将Delta分散到RDD中, trans和rel放在广播变量中,
 * 在外层遍历Delat的RDD,在内层遍历trans的广播变量,将新的结果追加到trans中
 * 终止条件是trans的size不再增长
 */
public class SplitDeltaSimplify {

    public static void main(String[] args) {

//        String inputFile = "plot_2";
//        String inputFile = "Mpds110_2";
        String inputFile = "plot";
//        String inputFile = "test";
        Container container = Container.parseInputFile("example/" + inputFile + ".pds");

        SparkConf conf = new SparkConf().setAppName("SplitDelta2").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

//        container.printRuleSet(); // check

        JavaRDD<int[]> delta = sc.parallelize(container.ruleSet);

        Accumulator<Integer> lastSum = sc.accumulator(-1);
        Accumulator<Integer> currSum = sc.accumulator(0);
        Accumulator<Integer> iterTime = sc.accumulator(0);

        int[] startConfg = container.startConf;
        int[][] startTrans = Transition.getStartTrans(startConfg);

        Map<Integer, Set<int[]>> transBucket = new ConcurrentHashMap<>();
        Set<Integer> existFromStates = new ConcurrentSet<>();

        for (int[] t : startTrans) {
            int sig = t[0] + t[1]; // for a transition (q, r, q'), we put it into the bucket with (q, r)
            if (transBucket.containsKey(sig)) {
                transBucket.get(sig).add(t);
            } else {
                Set<int[]> set = new ConcurrentSet<>();
                set.add(t);
                transBucket.put(sig, set);
                existFromStates.add(t[0]);
            }
        }
        Broadcast<Map<Integer, Set<int[]>>> bcTransBucket = sc.broadcast(transBucket);
        Broadcast<Set<Integer>> bcExistFromStates = sc.broadcast(existFromStates);

        Date startDate = new Date();

        while (!lastSum.value().equals(currSum.value())) {   // line 3
            iterTime.add(1);
            util.Util.logStart();
            util.Util.log("Iter time: ", iterTime.value());
            util.Util.log("size of last", lastSum.value());
            util.Util.log("size of current", currSum.value());
            util.Util.logEnd();

            lastSum.setValue(currSum.value());

            delta = delta.flatMap(transRule -> {
                List<int[]> flatMapRet = new ArrayList<>();

                // epsilon transRule
                if (transRule.length == 3) {
                    int[] newTransition = TransRule.toTransition(transRule);
                    int sig = newTransition[0] + newTransition[1]; // for a transition (q, r, q'), we put it into the bucket with (q, r)

                    if (existFromStates.contains(newTransition[2])) { // 新规则的finalState在已有规则的startState中
                        if (bcTransBucket.getValue().containsKey(sig)) {
                            bcTransBucket.getValue().get(sig).add(newTransition);
                        } else {
                            Set<int[]> set = new ConcurrentSet<int[]>();
                            set.add(newTransition);
                            bcTransBucket.getValue().put(sig, set);
                        }
                        bcExistFromStates.getValue().add(newTransition[0]);
                        currSum.add(1);
                    }
                } else {
                    int transRuleSig = transRule[2] + transRule[3];

                    if (bcTransBucket.getValue().containsKey(transRuleSig)) {
                        for (int[] t : bcTransBucket.getValue().get(transRuleSig)) {
                            int q_prime = t[2];
                            int sig = transRule[0] + transRule[1];

                            if (transRule.length == 4) { // line 7: check the size of stacks, we only accept size 1

                                int[] newTransition = new int[]{transRule[0], transRule[1], q_prime};


                                if (bcTransBucket.getValue().containsKey(sig)) {
                                    bcTransBucket.getValue().get(sig).add(newTransition);
                                } else {
                                    Set<int[]> set = new ConcurrentSet<>();
                                    set.add(newTransition);
                                    bcTransBucket.getValue().put(sig, set);
                                }
                                bcExistFromStates.getValue().add(newTransition[0]);
                                currSum.add(1);


                            } else if (transRule.length == 5) { // line 9: check the size of stacks
                                int gamma2 = transRule[4];

                                flatMapRet.add(new int[]{
                                        transRule[0],
                                        transRule[1],
                                        q_prime,
                                        gamma2
                                });

                                int tempSig = q_prime + gamma2;
                                if (bcTransBucket.getValue().containsKey(tempSig)) {
                                    for (int[] rel : bcTransBucket.getValue().get(tempSig)) { // line 11

                                        int[] newTransition = new int[]{transRule[0], transRule[1], rel[2]};

                                        if (bcTransBucket.getValue().containsKey(sig)) {
                                            bcTransBucket.getValue().get(sig).add(newTransition);
                                        } else {
                                            Set<int[]> set = new ConcurrentSet<>();
                                            set.add(newTransition);
                                            bcTransBucket.getValue().put(sig, set);
                                        }
                                        bcExistFromStates.getValue().add(newTransition[0]);
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

        Util.dumpToFile("output/" + inputFile + ".txt", Container.transfer(bcTransBucket.getValue().values()));
        Util.logEnd();

        Date endDate = new Date();

        Util.logStart();
        Util.log("Start time: ", startDate.getTime());
        Util.log("End time: ", endDate.getTime());
        Util.log("Duration: ", endDate.getTime() - startDate.getTime());
        Util.log("Total iter times", iterTime.value());
        Util.log("Size of result", bcTransBucket.getValue().size());
    }
}
