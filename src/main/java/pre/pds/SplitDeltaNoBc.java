//package pre.pds;
//
//import antlr.simple.Container;
//import io.netty.util.internal.ConcurrentSet;
//import model.pds.simple.Transition;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.broadcast.Broadcast;
//import org.apache.spark.util.LongAccumulator;
//import scala.Tuple3;
//import util.SparkUtil;
//import util.Symbol;
//import util.Util;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//
///**
// * Created by Cynric on 5/17/16.
// * 将Delta分散到RDD中, trans和rel放在广播变量中,
// * 在外层遍历Delat的RDD,在内层遍历trans的广播变量,将新的结果追加到trans中
// * 终止条件是trans的size不再增长
// * trans使用列表来存储
// * 将所有的string都映射成int,并且抛弃了面向对象,使用最基础的array和tuple来保存数据
// */
//public class SplitDeltaNoBc {
//
//    public static void main(String[] args) {
//
////        String inputFile = "plot_2";
////        String inputFile = "Mpds";
////        String inputFile = "Mpds110_2";
//        String inputFile = "plot";
////        String inputFile = "paper";
////        String inputFile = "test";
////        String inputFile = "test1";
////        String inputFile = "test2";
////        String inputFile = "test3";
//        Container container = Container.parseInputFile("test/" + inputFile + ".pds");
//
//        JavaSparkContext sc = SparkUtil.getJavaSparkContext();
//        System.out.println("Master config: " + sc.master());
//        int finalState = Symbol.getCode("__s__");
//
////        container.printRuleSet(); // check
//
//        LongAccumulator lastSum = sc.sc().longAccumulator();
//        lastSum.setValue(-1);
//        LongAccumulator currSum = sc.sc().longAccumulator();
//        LongAccumulator iterTime = sc.sc().longAccumulator();
//
//        int[] startConfg = container.startConf;
//        int[][] startTrans = Transition.getStartTrans(startConfg);
//
//        Set<Tuple3<Integer, Integer, Integer>> transSet = new ConcurrentSet<>();
//        Set<Integer> existFromStates = new ConcurrentSet<>();
//
//        for (int[] t : startTrans) {
//            transSet.add(new Tuple3(t[0], t[1], t[2]));
//            existFromStates.add(t[0]);
//        }
//
//        JavaRDD<int[]> delta = sc.parallelize(container.ruleSet);
//
//        Date startDate = new Date();
//
//        while (!lastSum.value().equals(currSum.value())) {   // line 3
////            Util.logStart();
//            iterTime.add(1);
//
//            lastSum.setValue(currSum.value());
//
//            delta = delta.flatMap(transRule -> {
//                List<int[]> flatMapRet = new ArrayList<>();
//                flatMapRet.add(transRule);
//
//
//                // epsilon transRule
//                if (transRule.length == 3) {
//                    if (bcExistFromStates.getValue().contains(transRule[2])) { // 若新规则的finalState在已有规则的startState中,则添加这条新规则
//                        Tuple3 t = new Tuple3(transRule[0], transRule[1], transRule[2]);
//                        addTransition(bcTransSet, bcExistFromStates, currSum, t);
//                        flatMapRet.remove(transRule);
//                    }
//                } else {
//                    for (Tuple3<Integer, Integer, Integer> t : bcTransSet.getValue()) {
//                        int q = t._1();
//                        int gamma = t._2();
//                        int q_prime = t._3();
//
//                        if (q == transRule[2] && gamma == transRule[3]) {
//                            if (transRule.length == 4) {
//                                Tuple3<Integer, Integer, Integer> newTransition = new Tuple3<Integer, Integer, Integer>(
//                                        transRule[0], transRule[1], q_prime
//                                );
//                                addTransition(bcTransSet, bcExistFromStates, currSum, newTransition);
//                            }
//
//
//                            if (transRule.length == 5) {
//                                int gamma2 = transRule[4];
//                                flatMapRet.add(new int[]{transRule[0], transRule[1], q_prime, gamma2});
//
////                                for (Tuple3<Integer, Integer, Integer> rel : bcTransSet.getValue()) {
////                                    if (q_prime == rel._1() && gamma2 == rel._2()) {
////                                        Tuple3<Integer, Integer, Integer> newTransition = new Tuple3<Integer, Integer, Integer>(
////                                                transRule[0], transRule[1], q_prime
////                                        );
////                                        addTransition(bcTransSet, bcExistFromStates, currSum, newTransition);
////                                    }
////                                }
//                            }
//                        }
//                    }
//                }
////                System.out.println(TransRule.toString(transRule) + " ---> " + Container.transfer(flatMapRet).toString());
//                return flatMapRet.iterator();
//            });
//            delta.count();
////            System.out.println(delta.count());
//        }
//
//        Date endDate = new Date();
//
//        Util.logStart();
////        Util.log("Start time: ", startDate.getTime());
////        Util.log("End time: ", endDate.getTime());
//        Util.log("Duration: ", endDate.getTime() - startDate.getTime() + "ms");
//        Util.log("Total iter times", iterTime.value());
//        Util.dumpToFile("output/" + inputFile + ".txt", Container.transfer(bcTransSet.getValue()));
//        Util.logEnd();
//    }
//
//    public static void addTransition(Broadcast<Set<Tuple3<Integer, Integer, Integer>>> bcTransSet,
//                                     Broadcast<Set<Integer>> bcExistFromStates,
//                                     LongAccumulator currSum, Tuple3<Integer, Integer, Integer> t) {
//        if (!bcTransSet.getValue().contains(t)) {
//            bcTransSet.getValue().add(t);
//            bcExistFromStates.getValue().add(t._1());
//            currSum.add(1);
//        }
//    }
//}
