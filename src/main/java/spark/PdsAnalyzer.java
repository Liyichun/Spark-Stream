package spark;

import antlr.simple.Container;
import model.pds.simple.Transition;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.util.LongAccumulator;
import scala.Tuple2;
import scala.Tuple3;
import util.Cantor;
import util.SparkUtil;
import util.Symbol;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Cynric on 9/15/16.
 */
public class PdsAnalyzer {

    private Set<Integer> Q_prime = new HashSet<>();

    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(SparkUtil.getSparkConf());
        System.out.println("Master config: " + sc.master());
        String inputFile = "paper";

        if (args.length > 0) {
            inputFile = args[0];
        }
        Container container = Container.parseInputFile("example/" + inputFile + ".pds");

        PdsAnalyzer pdsPre = new PdsAnalyzer();
        pdsPre.computePreStar(sc, container);

    }

    public static Tuple2<Integer, Integer> getTransTuple(int[] trans) {
        return new Tuple2<>(Cantor.codePair(trans[0], trans[1]), trans[2]);
    }

    public static Tuple2<Integer, Integer> getTransTuple(int x, int y, int z) {
        return new Tuple2<>(Cantor.codePair(x, y), z);
    }

    public void computePreStar(JavaSparkContext sc, Container container) {

        int[] startConfg = container.startConf;

        List<Tuple2<Integer, Integer>> startTrans = Transition.getStartTransList(startConfg);

        List<int[]> allRuleSet = container.getRuleSet();
        List<int[]> ruleSet4 = new ArrayList<>();
        List<int[]> ruleSet5 = new ArrayList<>();

        for (int[] rule : allRuleSet) {
            if (rule.length == 3) {
                startTrans.add(getTransTuple(rule)); // 能直接从空跳转得到迁移。
            } else if (rule.length == 4) {
                ruleSet4.add(rule);
            } else if (rule.length == 5) {
                ruleSet5.add(rule);
            }
        }

//        LongAccumulator lastAccum = sc.sc().longAccumulator();
//        LongAccumulator currAccum = sc.sc().longAccumulator();
//        currAccum.setValue(startTrans.size());
        LongAccumulator iterTime = sc.sc().longAccumulator();
//        LongAccumulator count = sc.sc().longAccumulator();
        long count = 0;
        int turns = 0;

        /*
         * delta，是指下推系统的迁移规则，有三种可能
         * 1. 空迁移
         * 2. omega为1的迁移
         * 3. omega为2的迁移
         * 我们分别用三个变量来存储这三种类型的迁移规则
         * 使用key－value的映射
         */
//        JavaPairRDD<Integer, int[]> deltaPair3 = sc.parallelize(ruleSet3).mapToPair(deltaPairFunc);
        JavaPairRDD<Integer, Tuple2<Integer, Integer>> deltaPair4 = sc.parallelize(ruleSet4).mapToPair(rule -> {
            Tuple2<Integer, Integer> tuple = new Tuple2<>(rule[0], rule[1]);
            int sig = Cantor.codePair(rule[2], rule[3]);
            return new Tuple2<>(sig, tuple);
        });
        JavaPairRDD<Integer, Tuple3<Integer, Integer, Integer>> deltaPair5 = sc.parallelize(ruleSet5).mapToPair(rule -> {
            Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(rule[0], rule[1], rule[4]);
            int sig = Cantor.codePair(rule[2], rule[3]);
            return new Tuple2<>(sig, tuple);
        });


        /* 所谓的Transition，指的是自动机的迁移状态，是一个三元组
         * 但是我们这里只要用两个Integer就可以存储
         * 做法是将前两个int编码成一个，因为我们不需要知道前两个具体是什么，只需要用它们做匹配
         * 因此使用Cantor编码将它们编码成一个int，最大限度节约空间
         */
        JavaPairRDD<Integer, Integer> transPair = sc.parallelizePairs(startTrans);
        JavaPairRDD<Integer, Integer> relPair = sc.parallelizePairs(new ArrayList<Tuple2<Integer, Integer>>());

        while (true) {
            iterTime.add(1);

            // 先处理长度为4的迁移
            JavaPairRDD<Integer, Tuple2<Tuple2<Integer, Integer>, Integer>> joinResult4 = deltaPair4.join(transPair); // TODO distinct

            // 找出匹配上的transition,把它们从trans集合里除去
            JavaPairRDD<Integer, Integer> matchedTrans = joinResult4.mapToPair(tuple -> {
                int sig = tuple._1;
                int toState = tuple._2._2;
                return new Tuple2<>(sig, toState);
            }); // TODO distinct


            JavaPairRDD<Integer, Integer> newTrans = joinResult4.values().mapToPair(tuple -> {
                Tuple2<Integer, Integer> rule = tuple._1;
                return getTransTuple(rule._1, rule._2, tuple._2);
            }); // TODO distinct


            // 再处理长度为5的迁移
            JavaPairRDD<Integer, Tuple2<Tuple3<Integer, Integer, Integer>, Integer>> joinResult5 = deltaPair5.join(transPair); // TODO distinct

            // 找出匹配上的transition,把它们从trans集合里除去
            JavaPairRDD<Integer, Integer> matchedTrans2 = joinResult5.mapToPair(tuple -> {
                int sig = tuple._1;
                int toState = tuple._2._2;
                return new Tuple2<>(sig, toState);
            }); // TODO distinct


            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newDeltaPair = joinResult5.values().mapToPair(tuple -> {
                Tuple3<Integer, Integer, Integer> rule = tuple._1;
                int toState = tuple._2;
                int sig = Cantor.codePair(toState, rule._3());
                return new Tuple2<>(sig, new Tuple2<>(rule._1(), rule._2()));
            }); // TODO distinct

            JavaPairRDD<Integer, Integer> newTrans2 = newDeltaPair.join(relPair).values().mapToPair(tuple -> {
                Tuple2<Integer, Integer> rule = tuple._1;
                return getTransTuple(rule._1, rule._2, tuple._2);
            }); // TODO distinct


            transPair = transPair.subtract(matchedTrans).subtract(matchedTrans2).union(newTrans).union(newTrans2).distinct(); // TODO distinct
            relPair = relPair.union(matchedTrans).union(matchedTrans2); // TODO distinct


            deltaPair4 = deltaPair4.union(newDeltaPair).distinct(); // TODO distinct

            count = newTrans.count() + newTrans2.count();
            if (count == 0) {
                turns += 1;
            } else {
                turns = 0;
            }


            Util.logStart();
            System.out.println("Iter Time: " + iterTime.sum());

            System.out.println("join Result4: " + joinResult4.count());
            for (Tuple2<Tuple2<Integer, Integer>, Integer> tuple : joinResult4.values().collect()) {
                StringBuffer sb = new StringBuffer();
                sb.append(Symbol.getString(tuple._1._1)).append(", ");
                sb.append(Symbol.getString(tuple._1._2)).append(", ");
                sb.append(Symbol.getString(tuple._2));
                System.out.println(sb.toString());
            }
            System.out.println("********");

            System.out.println("new count: " + count);
            System.out.println("turns: " + turns);
            System.out.println("********");

            System.out.println("matchedTrans1: " + matchedTrans.count());
            container.printTransPair(matchedTrans.collect());
            System.out.println("********");

            System.out.println("matchedTrans2: " + matchedTrans2.count());
            container.printTransPair(matchedTrans2.collect());
            System.out.println("********");

            System.out.println("new trans1: " + newTrans.count());
            container.printTransPair(newTrans.collect());
            System.out.println("********");

            System.out.println("new trans2: " + newTrans2.count());
            container.printTransPair(newTrans2.collect());
            System.out.println("********");

            System.out.println("Trans: " + transPair.count());
            container.printTransPair(transPair.collect());
            System.out.println("********");

            System.out.println("Rel: " + relPair.count());
            container.printTransPair(relPair.collect());
            System.out.println("********");

            System.out.println("New Delta: " + newDeltaPair.count());
            container.printDelta4List(newDeltaPair.collect());
            System.out.println("********");

            System.out.println("Delta4 : " + deltaPair4.count());
            container.printDelta4List(deltaPair4.collect());
            Util.logEnd();

            if (transPair.count() == 0 || turns == 2) { // 连续两轮没有找到新的trans，就终止
                break;
            }
        }

        relPair = relPair.union(transPair);
        System.out.println("Trans size: " + relPair.values().count());
        container.printTransPair(relPair.collect());
    }


    public void computePostStar(JavaSparkContext sc, Container container) {
        int[] startConfg = container.startConf;
        List<Tuple2<Integer, Integer>> startTrans = Transition.getStartTransList(startConfg);

        List<int[]> allRuleSet = container.getRuleSet();
        List<int[]> emptyRuleSet = new ArrayList<>();
        List<int[]> ruleSet4 = new ArrayList<>();
        List<int[]> ruleSet5 = new ArrayList<>();


        for (int[] rule : allRuleSet) {
            Q_prime.add(rule[0]);
            Q_prime.add(rule[2]);
            if (rule.length == 3) {
                emptyRuleSet.add(rule);
            } else if (rule.length == 4) {
                ruleSet4.add(rule);
            } else if (rule.length == 5) {
                int combinedState = Symbol.getCodeForPostState(rule[2], rule[3]);
                Q_prime.add(combinedState);
                ruleSet5.add(rule);
            }
        }


        JavaPairRDD<Integer, Integer> deltaPair3 =
                sc.parallelize(emptyRuleSet).mapToPair(rule -> {
                    int sig = Cantor.codePair(rule[0], rule[1]);
                    return new Tuple2<>(sig, rule[2]);
                });

        JavaPairRDD<Integer, Tuple2<Integer, Integer>> deltaPair4 =
                sc.parallelize(ruleSet4).mapToPair(rule -> {
                    int sig = Cantor.codePair(rule[0], rule[1]);
                    Tuple2<Integer, Integer> tuple = new Tuple2<>(rule[2], rule[3]);
                    return new Tuple2<>(sig, tuple);
                });

        JavaPairRDD<Integer, Tuple3<Integer, Integer, Integer>> deltaPair5 =
                sc.parallelize(ruleSet5).mapToPair(rule -> {
                    int sig = Cantor.codePair(rule[0], rule[1]);
                    Tuple3<Integer, Integer, Integer> tuple = new Tuple3<>(rule[2], rule[3], rule[4]);
                    return new Tuple2<>(sig, tuple);
                });


        JavaPairRDD<Integer, Integer> transPair = sc.parallelizePairs(startTrans); // sig([0], [1]) as key, [2] as value.

        JavaPairRDD<Integer, Integer> emptyTransPair = sc.parallelizePairs(startTrans); // [2] as key, [0] as value

        JavaPairRDD<Integer, Tuple2<Integer, Integer>> relPair =
                sc.parallelizePairs(new ArrayList<Tuple2<Integer, Tuple2<Integer, Integer>>>()); // [0] as key, tuple<[1], [2]> as value
        JavaPairRDD<Integer, Integer> emptyRelPair =
                sc.parallelizePairs(new ArrayList<Tuple2<Integer, Integer>>()); // [2] as key, [0] as value

        long count = 0;
        int turns = 0;

        while (true) {
            JavaPairRDD<Integer, Tuple2<Integer, Integer>> joinResult3 = deltaPair3.join(transPair);

            // 找出匹配上的transition,把它们从trans集合里除去
            JavaPairRDD<Integer, Integer> matchedTrans3 = joinResult3.mapToPair(tuple -> {
                int sig = tuple._1;
                int toState = tuple._2._2;
                return new Tuple2<>(sig, toState);
            }); // TODO distinct

            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newRel3 = joinResult3.mapToPair(
                    tuple -> {
                        int sig = tuple._1;
                        int[] from = Cantor.dePair(sig);
                        return new Tuple2<>(
                                from[0],
                                new Tuple2<>(from[1], tuple._2._2)
                        );
                    }
            );


            JavaPairRDD<Integer, Integer> newTrans3 = joinResult3.values().mapToPair(
                    tuple -> {
                        return new Tuple2<>(tuple._2, tuple._1); // emptyTransPair uses [2] as key, [0] as val
                    }
            );


            emptyTransPair = emptyTransPair.union(newTrans3);
            transPair = transPair.subtract(matchedTrans3);
            relPair = relPair.union(newRel3);


            JavaPairRDD<Integer, Tuple2<Tuple2<Integer, Integer>, Integer>> joinResult4 =
                    deltaPair4.join(transPair);

            // 找出匹配上的transition,把它们从trans集合里除去
            JavaPairRDD<Integer, Integer> matchedTrans4 = joinResult4.mapToPair(tuple -> {
                int sig = tuple._1;
                int toState = tuple._2._2;
                return new Tuple2<>(sig, toState);
            }); // TODO distinct

            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newRel4 = joinResult4.mapToPair(
                    tuple -> {
                        int sig = tuple._1;
                        int[] from = Cantor.dePair(sig);
                        return new Tuple2<>(
                                from[0],
                                new Tuple2<>(from[1], tuple._2._2)
                        );
                    }
            );


            JavaPairRDD<Integer, Integer> newTrans4 = joinResult4.values().mapToPair(
                    tuple -> {
                        Tuple2<Integer, Integer> rule = tuple._1;
                        return getTransTuple(rule._1, rule._2, tuple._2);
                    }); // TODO distinct

            transPair = transPair.union(newTrans4).subtract(matchedTrans4);
            relPair = relPair.union(newRel4);


            // 再处理长度为5的迁移
            JavaPairRDD<Integer, Tuple2<Tuple3<Integer, Integer, Integer>, Integer>> joinResult5 = deltaPair5.join(transPair); // TODO distinct

            // 找出匹配上的transition,把它们从trans集合里除去
            JavaPairRDD<Integer, Integer> matchedTrans5 = joinResult5.mapToPair(tuple -> {
                int sig = tuple._1;
                int toState = tuple._2._2;
                return new Tuple2<>(sig, toState);
            }); // TODO distinct

            JavaPairRDD<Integer, Integer> newTrans5 = joinResult5.values().mapToPair(tuple -> {
                Tuple3<Integer, Integer, Integer> rule = tuple._1;
                int combineState = Symbol.getCodeForPostState(rule._1(), rule._2());
                return getTransTuple(rule._1(), rule._2(), combineState);
            });

            JavaPairRDD<Integer, Tuple2<Integer, Integer>> newRel5 = joinResult5.flatMapToPair(tuple -> {
                List<Tuple2<Integer, Tuple2<Integer, Integer>>> ret = new ArrayList<>();
                int sig = tuple._1;
                int[] from = Cantor.dePair(sig);
                int toState = tuple._2._2;
                ret.add(new Tuple2<>(
                        from[0],
                        new Tuple2<>(from[1], toState)
                ));

                Tuple3<Integer, Integer, Integer> rule = tuple._2._1;
                int combineState = Symbol.getCodeForPostState(rule._1(), rule._2());
                ret.add(new Tuple2<>(
                        combineState,
                        new Tuple2<>(rule._3(), toState)
                ));

                return ret.listIterator();
            });

            JavaPairRDD<Integer, Integer> newInnerTrans = newRel5.join(emptyRelPair).values().mapToPair(tuple -> {
                return getTransTuple(tuple._2, tuple._1._1, tuple._1._2);
            });


            transPair = transPair.union(newTrans5).union(newInnerTrans).subtract(matchedTrans5);
            relPair = relPair.union(newRel5);


            // <q, <p, <r', q'>>>
            JavaPairRDD<Integer, Tuple2<Integer, Tuple2<Integer, Integer>>> emptyJoinResult =
                    emptyTransPair.join(relPair);


            JavaPairRDD<Integer, Integer> matchedEmptyTrans = emptyJoinResult.mapToPair(tuple -> {
                int q = tuple._1;
                int p = tuple._2._1;
                return new Tuple2<>(q, p);
            }); // TODO distinct

            JavaPairRDD<Integer, Integer> newEmptyTrans = emptyJoinResult.values().mapToPair(tuple -> {
                return getTransTuple(tuple._1, tuple._2._1, tuple._2._2);
            });

            emptyTransPair = emptyTransPair.subtract(matchedEmptyTrans);
            emptyRelPair = emptyRelPair.union(matchedEmptyTrans);
            transPair = transPair.union(newEmptyTrans);

            count = newTrans3.count() + newTrans4.count() + newTrans5.count() + newInnerTrans.count();

            if (count == 0) {
                turns += 1;
            } else {
                turns = 0;
            }

            if (transPair.count() == 0 || turns == 2) { // 连续两轮没有找到新的trans，就终止
                break;
            }

        }

    }
}
