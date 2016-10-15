//package analyzer.parallel;
//
//import analyzer.PdsAnalyzer;
//import antlr.simple.Container;
//import model.pds.simple.Transition;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.broadcast.Broadcast;
//import org.apache.spark.util.CollectionAccumulator;
//import org.apache.spark.util.LongAccumulator;
//import scala.Tuple2;
//import scala.Tuple3;
//import util.Cantor;
//import util.SparkUtil;
//import util.Symbol;
//import util.Util;
//
//import java.util.*;
//
//import static com.sun.tools.javac.jvm.ByteCodes.ret;
//import static org.apache.avro.TypeEnum.c;
//
//
///**
// * Created by Cynric on 9/15/16.
// */
//public class ParallelPdsAnalyzerUsingBC extends PdsAnalyzer {
//
//    static Function<Integer, Set<Integer>> createCombiner =
//            a -> {
//                Set<Integer> ret = new HashSet<>();
//                ret.add(a);
//                return ret;
//            };
//
//    static Function2<Set<Integer>, Integer, Set<Integer>> mergeValue =
//            (set, a) -> {
//                set.add(a);
//                return set;
//            };
//
//    static Function2<Set<Integer>, Set<Integer>, Set<Integer>> mergeCombiners =
//            (set1, set2) -> {
//                set1.addAll(set2);
//                return set1;
//            };
//
//    private Set<Integer> Q_prime = new HashSet<>();
//
//    public static void main(String[] args) {
//        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());
//        System.out.println("Master config: " + sc.master());
//        String inputFile = "paper";
//
//        if (args.length > 0) {
//            inputFile = args[0];
//        }
//        Container container = Container.parseInputFile("example/" + inputFile + ".pds");
//
//        ParallelPdsAnalyzerUsingBC pdsPre = new ParallelPdsAnalyzerUsingBC();
//        pdsPre.computePreStar(sc, container);
//
//    }
//
//    /**
//     * 使用broadcast实现的 map-side join来尝试优化性能
//     *
//     * @param sc
//     * @param container
//     */
//    public void computePreStar(JavaSparkContext sc, Container container) {
//
//        int[] startConfg = container.startConf;
//
//        List<Tuple2<Integer, Integer>> startTrans = Transition.getStartTransList(startConfg);
//
//        List<int[]> allRuleSet = container.getRuleSet();
//        List<int[]> ruleSet4 = new ArrayList<>();
//        List<int[]> ruleSet5 = new ArrayList<>();
//
//        for (int[] rule : allRuleSet) {
//            if (rule.length == 3) {
//                startTrans.add(getTransTuple(rule)); // 能直接从空跳转得到迁移。
//            } else if (rule.length == 4) {
//                ruleSet4.add(rule);
//            } else if (rule.length == 5) {
//                ruleSet5.add(rule);
//            }
//        }
//
//        LongAccumulator iterTime = sc.sc().longAccumulator();
//        long count = 0;
//        int turns = 0;
//
//        /*
//         * delta，是指下推系统的迁移规则，有三种可能
//         * 1. 空迁移
//         * 2. omega为1的迁移
//         * 3. omega为2的迁移
//         * 我们分别用三个变量来存储这三种类型的迁移规则
//         * 使用key－value的映射
//         */
////        JavaPairRDD<Integer, int[]> deltaPair3 = sc.parallelize(ruleSet3).mapToPair(deltaPairFunc);
//        JavaPairRDD<Integer, Tuple2<Integer, Integer>> deltaPair4 =
//                sc.parallelize(ruleSet4).mapToPair(rule -> {
//                    Tuple2<Integer, Integer> tuple = new Tuple2<>(rule[0], rule[1]);
//                    int sig = Cantor.codePair(rule[2], rule[3]);
//                    return new Tuple2<>(sig, tuple);
//                }).cache();
//
//        JavaPairRDD<Integer, Tuple3<Integer, Integer, Integer>> deltaPair5 =
//                sc.parallelize(ruleSet5).mapToPair(rule -> {
//                    Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(rule[0], rule[1], rule[4]);
//                    int sig = Cantor.codePair(rule[2], rule[3]);
//                    return new Tuple2<>(sig, tuple);
//                }).cache();
//
//
//        /* 所谓的Transition，指的是自动机的迁移状态，是一个三元组
//         * 但是我们这里只要用两个Integer就可以存储
//         * 做法是将前两个int编码成一个，因为我们不需要知道前两个具体是什么，只需要用它们做匹配
//         * 因此使用Cantor编码将它们编码成一个int，最大限度节约空间
//         */
//        Broadcast<Map<Integer, Set<Integer>>> trans_bc = sc.broadcast(transTupleListToMap(startTrans));
//
//        while (true) {
//            iterTime.add(1);
//
//
//            JavaPairRDD<Integer, Integer> newTransPair =
//                    deltaPair4.flatMapToPair(tuple -> {
//                        Map<Integer, Set<Integer>> transBucket = trans_bc.getValue();
//                        Set<Tuple2<Integer, Integer>> ret = new HashSet<>();
//
//                        Integer sig = tuple._1;
//                        if (transBucket.containsKey(sig)) {
//                            Tuple2<Integer, Integer> rule = tuple._2;
//                            int newTranSig = Cantor.codePair(rule._1, rule._2);
//
//                            for (Integer toState : transBucket.get(sig)) {
//                                Tuple2<Integer, Integer> newTrans = new Tuple2<>(
//                                        newTranSig,
//                                        toState
//                                );
//                                if (!transBucket.containsKey(newTranSig) ||
//                                        !transBucket.get(newTranSig).contains(toState)) {
//                                    ret.add(newTrans);
//                                }
//                            }
//                        }
//                        return ret.iterator();
//                    });
//
////            JavaPairRDD<Integer, Set<Integer>> combineRes =
////                    newTransPair.combineByKey(createCombiner, mergeValue, mergeCombiners);
//
//
//            JavaPairRDD<Integer, Tuple2<Integer, Integer> newDeltaPair =
//                    deltaPair5.flatMapToPair(tuple -> {
//                        Map<Integer, Set<Integer>> transBucket = trans_bc.getValue();
//                        Set<Tuple2<Integer, Integer>> ret = new HashSet<>();
//
//                        Integer sig = tuple._1;
//                        if (transBucket.containsKey(sig)) {
//                            Tuple2<Integer, Integer> rule = tuple._2;
//                            int newTranSig = Cantor.codePair(rule._1, rule._2);
//
//                            for (Integer toState : transBucket.get(sig)) {
//                                Tuple2<Integer, Integer> newTrans = new Tuple2<>(
//                                        newTranSig,
//                                        toState
//                                );
//                                if (!transBucket.containsKey(newTranSig) ||
//                                        !transBucket.get(newTranSig).contains(toState)) {
//                                    ret.add(newTrans);
//                                }
//                            }
//                        }
//                        return ret.iterator();
//                    });
//
//
//
//            List<Tuple2<Integer, Integer>> newTransList = newTransPair.collect();
//
//            Map<Integer, Set<Integer>> transBucket = trans_bc.getValue();
//            for (Tuple2<Integer, Integer> newTrans : newTransList) {
//                Integer key = newTrans._1;
//                Integer value = newTrans._2;
//
//                if (transBucket.containsKey(key) && !transBucket.get(key).contains(value)) {
//                    transBucket.get(key).add(value);
//                    count += 1;
//                } else {
//                    Set<Integer> set = new HashSet<>();
//                    set.add(value);
//                    transBucket.put(key, set);
//                    count += 1;
//                }
//            }
//            trans_bc = sc.broadcast(transBucket); // re-broadcast
//
//            if (count == 0) {
//                turns += 1;
//            } else {
//                turns = 0;
//            }
//
//
//            Util.logStart();
//            System.out.println("Iter Time: " + iterTime.sum());
//
//            Util.logEnd();
//
//            if (turns == 2) { // 连续两轮没有找到新的trans，就终止
//                break;
//            }
//        }
//    }
//
//
//    public void computePostStar(JavaSparkContext sc, Container container) {
//        int[] startConfg = container.startConf;
//        List<Tuple2<Integer, Integer>> startTrans = Transition.getStartTransList(startConfg);
//
//        List<int[]> allRuleSet = container.getRuleSet();
//        List<int[]> emptyRuleSet = new ArrayList<>();
//        List<int[]> ruleSet4 = new ArrayList<>();
//        List<int[]> ruleSet5 = new ArrayList<>();
//
//
//        for (int[] rule : allRuleSet) {
//            Q_prime.add(rule[0]);
//            Q_prime.add(rule[2]);
//            if (rule.length == 3) {
//                emptyRuleSet.add(rule);
//            } else if (rule.length == 4) {
//                ruleSet4.add(rule);
//            } else if (rule.length == 5) {
//                int combinedState = Symbol.getCodeForPostState(rule[2], rule[3]);
//                Q_prime.add(combinedState);
//                ruleSet5.add(rule);
//            }
//        }
//
//
//        JavaPairRDD<Integer, Integer> deltaPair3 =
//                sc.parallelize(emptyRuleSet).mapToPair(rule -> {
//                    int sig = Cantor.codePair(rule[0], rule[1]);
//                    return new Tuple2<>(sig, rule[2]);
//                });
//
//        JavaPairRDD<Integer, Tuple2<Integer, Integer>> deltaPair4 =
//                sc.parallelize(ruleSet4).mapToPair(rule -> {
//                    int sig = Cantor.codePair(rule[0], rule[1]);
//                    Tuple2<Integer, Integer> tuple = new Tuple2<>(rule[2], rule[3]);
//                    return new Tuple2<>(sig, tuple);
//                });
//
//        JavaPairRDD<Integer, Tuple3<Integer, Integer, Integer>> deltaPair5 =
//                sc.parallelize(ruleSet5).mapToPair(rule -> {
//                    int sig = Cantor.codePair(rule[0], rule[1]);
//                    Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(rule[2], rule[3], rule[4]);
//                    return new Tuple2<>(sig, tuple);
//                });
//
//
//        JavaPairRDD<Integer, Integer> transPair = sc.parallelizePairs(startTrans); // sig([0], [1]) as key, [2] as value.
//
//        JavaPairRDD<Integer, Integer> emptyTransPair = sc.parallelizePairs(startTrans); // [2] as key, [0] as value
//
//        JavaPairRDD<Integer, Tuple2<Integer, Integer>> relPair =
//                sc.parallelizePairs(new ArrayList<Tuple2<Integer, Tuple2<Integer, Integer>>>()); // [0] as key, tuple<[1], [2]> as value
//        JavaPairRDD<Integer, Integer> emptyRelPair =
//                sc.parallelizePairs(new ArrayList<Tuple2<Integer, Integer>>()); // [2] as key, [0] as value
//
//        long count = 0;
//        int turns = 0;
//
//        while (true) {
//            JavaPairRDD<Integer, Tuple2<Integer, Integer>> joinResult3 = deltaPair3.join(transPair);
//
//            // 找出匹配上的transition,把它们从trans集合里除去
//            JavaPairRDD<Integer, Integer> matchedTrans3 = joinResult3.mapToPair(tuple -> {
//                int sig = tuple._1;
//                int toState = tuple._2._2;
//                return new Tuple2<>(sig, toState);
//            }); // TODO distinct
//
//            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newRel3 = joinResult3.mapToPair(
//                    tuple -> {
//                        int sig = tuple._1;
//                        int[] from = Cantor.dePair(sig);
//                        return new Tuple2<>(
//                                from[0],
//                                new Tuple2<>(from[1], tuple._2._2)
//                        );
//                    }
//            );
//
//
//            JavaPairRDD<Integer, Integer> newTrans3 = joinResult3.values().mapToPair(
//                    tuple -> {
//                        return new Tuple2<>(tuple._2, tuple._1); // emptyTransPair uses [2] as key, [0] as val
//                    }
//            );
//
//
//            emptyTransPair = emptyTransPair.union(newTrans3);
//            transPair = transPair.subtract(matchedTrans3);
//            relPair = relPair.union(newRel3);
//
//
//            JavaPairRDD<Integer, Tuple2<Tuple2<Integer, Integer>, Integer>> joinResult4 =
//                    deltaPair4.join(transPair);
//
//            // 找出匹配上的transition,把它们从trans集合里除去
//            JavaPairRDD<Integer, Integer> matchedTrans4 = joinResult4.mapToPair(tuple -> {
//                int sig = tuple._1;
//                int toState = tuple._2._2;
//                return new Tuple2<>(sig, toState);
//            }); // TODO distinct
//
//            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newRel4 = joinResult4.mapToPair(
//                    tuple -> {
//                        int sig = tuple._1;
//                        int[] from = Cantor.dePair(sig);
//                        return new Tuple2<>(
//                                from[0],
//                                new Tuple2<>(from[1], tuple._2._2)
//                        );
//                    }
//            );
//
//
//            JavaPairRDD<Integer, Integer> newTrans4 = joinResult4.values().mapToPair(
//                    tuple -> {
//                        Tuple2<Integer, Integer> rule = tuple._1;
//                        return getTransTuple(rule._1, rule._2, tuple._2);
//                    }); // TODO distinct
//
//            transPair = transPair.union(newTrans4).subtract(matchedTrans4);
//            relPair = relPair.union(newRel4);
//
//
//            // 再处理长度为5的迁移
//            JavaPairRDD<Integer, Tuple2<Tuple3<Integer, Integer, Integer>, Integer>> joinResult5 = deltaPair5.join(transPair); // TODO distinct
//
//            // 找出匹配上的transition,把它们从trans集合里除去
//            JavaPairRDD<Integer, Integer> matchedTrans5 = joinResult5.mapToPair(tuple -> {
//                int sig = tuple._1;
//                int toState = tuple._2._2;
//                return new Tuple2<>(sig, toState);
//            }); // TODO distinct
//
//            JavaPairRDD<Integer, Integer> newTrans5 = joinResult5.values().mapToPair(tuple -> {
//                Tuple3<Integer, Integer, Integer> rule = tuple._1;
//                int combineState = Symbol.getCodeForPostState(rule._1(), rule._2());
//                return getTransTuple(rule._1(), rule._2(), combineState);
//            });
//
//            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newRel5 = joinResult5.flatMapToPair(tuple -> {
//                List<Tuple2<Integer, Tuple2<Integer, Integer>>> ret = new ArrayList<>();
//                int sig = tuple._1;
//                int[] from = Cantor.dePair(sig);
//                int toState = tuple._2._2;
//                ret.add(new Tuple2<>(
//                        from[0],
//                        new Tuple2<>(from[1], toState)
//                ));
//
//                Tuple3<Integer, Integer, Integer> rule = tuple._2._1;
//                int combineState = Symbol.getCodeForPostState(rule._1(), rule._2());
//                ret.add(new Tuple2<>(
//                        combineState,
//                        new Tuple2<>(rule._3(), toState)
//                ));
//
//                return ret.listIterator();
//            });
//
//            JavaPairRDD<Integer, Integer> newInnerTrans = newRel5.join(emptyRelPair).values().mapToPair(tuple -> {
//                return getTransTuple(tuple._2, tuple._1._1, tuple._1._2);
//            });
//
//
//            transPair = transPair.union(newTrans5).union(newInnerTrans).subtract(matchedTrans5);
//            relPair = relPair.union(newRel5);
//
//
//            // <q, <p, <r', q'>>>
//            JavaPairRDD<Integer, Tuple2<Integer, Tuple2<Integer, Integer>>> emptyJoinResult =
//                    emptyTransPair.join(relPair);
//
//
//            JavaPairRDD<Integer, Integer> matchedEmptyTrans = emptyJoinResult.mapToPair(tuple -> {
//                int q = tuple._1;
//                int p = tuple._2._1;
//                return new Tuple2<>(q, p);
//            }); // TODO distinct
//
//            JavaPairRDD<Integer, Integer> newEmptyTrans = emptyJoinResult.values().mapToPair(tuple -> {
//                return getTransTuple(tuple._1, tuple._2._1, tuple._2._2);
//            });
//
//            emptyTransPair = emptyTransPair.subtract(matchedEmptyTrans);
//            emptyRelPair = emptyRelPair.union(matchedEmptyTrans);
//            transPair = transPair.union(newEmptyTrans);
//
//            count = newTrans3.count() + newTrans4.count() + newTrans5.count() + newInnerTrans.count();
//
//            if (count == 0) {
//                turns += 1;
//            } else {
//                turns = 0;
//            }
//
//            if (transPair.count() == 0 || turns == 2) { // 连续两轮没有找到新的trans，就终止
//                break;
//            }
//
//        }
//
//    }
//}
