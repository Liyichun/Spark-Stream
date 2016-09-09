package pre.pds;

import antlr.simple.Container;
import io.netty.util.internal.ConcurrentSet;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import model.pds.simple.*;
import util.Cantor;
import util.Util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Cynric on 5/17/16.
 * 将Delta分散到RDD中, trans和rel放在广播变量中,
 * 在外层遍历Delat的RDD,在内层遍历trans的广播变量,将新的结果追加到trans中
 * 终止条件是trans的size不再增长
 * 将所有的string都映射成int,并且抛弃了面向对象,使用最基础的array和tuple来保存数据
 */
public class SplitDeltaSimplifyCantor {

    public static void main(String[] args) {

//        String inputFile = "plot_2";
//        String inputFile = "Mpds";
//        String inputFile = "Mpds110_2";
        String inputFile = "plot";
//        String inputFile = "paper";
//        String inputFile = "test";
//        String inputFile = "test1";
//        String inputFile = "test2";
//        String inputFile = "test3";
        Container container = Container.parseInputFile("example/" + inputFile + ".pds");

        SparkConf conf = new SparkConf().setAppName("SplitDeltaSimplifyCantor").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        int finalState = "__s__".hashCode();

//        container.printRuleSet(); // check

        Accumulator<Integer> lastSum = sc.accumulator(-1);
        Accumulator<Integer> currSum = sc.accumulator(0);
        Accumulator<Integer> iterTime = sc.accumulator(0);

        int[] startConfg = container.startConf;
        int[][] startTrans = Transition.getStartTrans(startConfg);

        Map<Integer, Set<Integer>> transBucket = new ConcurrentHashMap<>();
        Set<Integer> existFromStates = new ConcurrentSet<>();

        for (int[] t : startTrans) {
            int sig = Cantor.codePair(t[0], t[1]); // for a transition (q, r, q'), we put it into the bucket with (q, r)
            if (transBucket.containsKey(sig)) {
                transBucket.get(sig).add(t[2]);
            } else {
                Set<Integer> set = new ConcurrentSet<>();
                set.add(t[2]);
                transBucket.put(sig, set);
                existFromStates.add(t[0]);
            }
        }
        Broadcast<Map<Integer, Set<Integer>>> bcTransBucket = sc.broadcast(transBucket);
        Broadcast<Set<Integer>> bcExistFromStates = sc.broadcast(existFromStates);

        JavaRDD<int[]> delta = sc.parallelize(container.ruleSet);

        Date startDate = new Date();

        while (!lastSum.value().equals(currSum.value())) {   // line 3
//            Util.logStart();
            iterTime.add(1);

            lastSum.setValue(currSum.value());

            delta = delta.flatMap(transRule -> {
                List<int[]> flatMapRet = new ArrayList<>();
                flatMapRet.add(transRule);

                // epsilon transRule
                if (transRule.length == 3) {
                    int sig = Cantor.codePair(transRule[0], transRule[1]); // for a transition (q, r, q'), we put it into the bucket with (q, r)

                    if (bcExistFromStates.getValue().contains(transRule[2])) { // 若新规则的finalState在已有规则的startState中,则添加这条新规则
                        addTransition(bcTransBucket, bcExistFromStates, currSum, sig, transRule[0], transRule[2]);
                        flatMapRet.remove(transRule);
                    }
                } else {
                    int q_gamma_sig = Cantor.codePair(transRule[2], transRule[3]);

                    if (bcTransBucket.getValue().containsKey(q_gamma_sig)) {
                        int sig = Cantor.codePair(transRule[0], transRule[1]);
                        for (int q_prime : bcTransBucket.getValue().get(q_gamma_sig)) {

                            if (transRule.length == 4) { // line 7: check the size of stacks, we only accept size 1
                                addTransition(bcTransBucket, bcExistFromStates, currSum, sig, transRule[0], q_prime);
                            } else { // line 9: check the size of stacks
                                if (q_prime == finalState) {
                                    addTransition(bcTransBucket, bcExistFromStates, currSum, sig, transRule[0], q_prime);
                                } else {
                                    int gamma2 = transRule[4];

                                    flatMapRet.add(new int[]{
                                            transRule[0],
                                            transRule[1],
                                            q_prime,
                                            gamma2
                                    });

                                    int tempSig = Cantor.codePair(q_prime, gamma2);

                                    if (bcTransBucket.getValue().containsKey(tempSig)) {
                                        for (int q_prime2 : bcTransBucket.getValue().get(tempSig)) { // line 11
                                            addTransition(bcTransBucket, bcExistFromStates, currSum, sig, transRule[0], q_prime2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
//                System.out.println(TransRule.toString(transRule) + " ---> " + Container.transfer(flatMapRet).toString());
                return flatMapRet;
            });
            delta.count();
//            System.out.println(delta.count());
        }

        Date endDate = new Date();

        Util.logStart();
//        Util.log("Start time: ", startDate.getTime());
//        Util.log("End time: ", endDate.getTime());
        Util.log("Duration: ", endDate.getTime() - startDate.getTime() + "ms");
        Util.log("Total iter times", iterTime.value());
        Util.dumpToFile("output/" + inputFile + ".txt", Container.transferCantor(bcTransBucket.getValue()));
        Util.logEnd();
    }

    public static void addTransition(Broadcast<Map<Integer, Set<Integer>>> bcTransBucket,
                                     Broadcast<Set<Integer>> bcExistFromStates,
                                     Accumulator currSum, int sig, int startState, int toState) {
        if (bcTransBucket.getValue().containsKey(sig)) {
            if (!bcTransBucket.getValue().get(sig).contains(toState)) {
                bcTransBucket.getValue().get(sig).add(toState);
                bcExistFromStates.getValue().add(startState);
                currSum.add(1);
            }
        } else {
            Set<Integer> set = new ConcurrentSet<>();
            set.add(toState);
            bcTransBucket.getValue().put(sig, set);
            bcExistFromStates.getValue().add(startState);
            currSum.add(1);
        }

    }
}
