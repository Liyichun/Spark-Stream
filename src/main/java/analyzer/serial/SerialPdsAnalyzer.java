package analyzer.serial;

import analyzer.PdsAnalyzer;
import antlr.simple.Container;
import model.pds.simple.Transition;
import scala.Tuple3;

import java.util.*;


/**
 * Created by Cynric on 12/10/2016.
 */
public class SerialPdsAnalyzer extends PdsAnalyzer {
    public void computePre(Container container) {
        int[] startConfg = container.startConf;

        List<Tuple3<Integer, Integer, Integer>> startTrans = Transition.getStartTransList(startConfg, false);

        List<int[]> allRuleSet = container.getRuleSet();

        for (int[] rule : allRuleSet) {
            if (rule.length == 3) {
                startTrans.add(
                        new Tuple3<>(rule[0], rule[1], rule[2])
                ); // 能直接从空跳转得到迁移。
            }
        }

        Queue<Tuple3<Integer, Integer, Integer>> trans = new LinkedList<>();
        trans.addAll(startTrans);
        Set<Tuple3<Integer, Integer, Integer>> rel = new HashSet<>();

        Date startDate = new Date();
        while (true) {
            Tuple3<Integer, Integer, Integer> t = trans.poll();
            if (t == null) {
                break;
            }

            if (!rel.contains(t)) {
                rel.add(t);

                int q = t._1();
                int gamma = t._2();
                int q_prime = t._3();

                for (int i = 0; i < allRuleSet.size(); i++) {
                    int[] rule = allRuleSet.get(i);
                    if (rule.length == 3) {
                        continue;
                    }
                    if (q == rule[2] && gamma == rule[3]) {
                        if (rule.length == 4) {
                            trans.add(
                                    new Tuple3<>(rule[0], rule[1], q_prime)
                            );
                        } else if (rule.length == 5) {
                            int gamma2 = rule[4];
                            allRuleSet.add(new int[]{rule[0], rule[1], q_prime, gamma2});

                            for (Tuple3<Integer, Integer, Integer> r : rel) {
                                if (q_prime == r._1() && gamma2 == r._2()) {
                                    trans.add(
                                            new Tuple3<>(rule[0], rule[1], r._3())
                                    );
                                }
                            }
                        }
                    }
                }

            }
        }
        Date endDate = new Date();

        System.out.println(rel.size());
        printTranSet(rel);

        System.out.println(endDate.getTime() - startDate.getTime());
    }
}
