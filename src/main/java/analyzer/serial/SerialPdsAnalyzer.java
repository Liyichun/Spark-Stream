package analyzer.serial;

import analyzer.PdsAnalyzer;
import antlr.simple.Container;
import model.pds.simple.Transition;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static analyzer.parallel.PdsAnalyzer2.getTransTuple;

/**
 * Created by Cynric on 12/10/2016.
 */
public class SerialPdsAnalyzer extends PdsAnalyzer {
    public void computePre(Container container) {
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




    }
}
