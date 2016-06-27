import antlr.PdsBaseVisitor;
import antlr.PdsLexer;
import antlr.PdsParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.broadcast.Broadcast;
import pds.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by Cynric on 5/17/16.
 */
public class SimpleApp {

    public static void main(String[] args) {

        List<TransRule> ruleSet = parseInputFile("example/plot.pds");

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> lines = sc.textFile("example/plot.pds").cache();
//        long length = lines.count();
//        Util.log(String.valueOf(length));
//        Util.log(lines.toString());

        JavaRDD<TransRule> inputData = sc.parallelize(ruleSet);

        Broadcast<Set<TransRule>> bcDelta = sc.broadcast(new HashSet<>());
        Broadcast<Set<TransRule>> bcDelta_prime = sc.broadcast(new HashSet<>());
        Broadcast<Set<Transition>> bcTrans = sc.broadcast(new HashSet<>());
        Broadcast<Set<Transition>> bcRel = sc.broadcast(new HashSet<>());


        inputData.foreach(transRule -> {
            bcDelta.getValue().add(transRule); // add data into bcDelta

            // find all <p, r> -> <p', e>   line2 of pseudocode
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
        });

        Util.log("The size of trans after line2", bcTrans.getValue().size());

        for (Transition t : bcTrans.getValue()) { // line 3 - 4
            String q = t.getStartState();
            String gamma = t.getAlphabet();
            String q_prime = t.getFinalState();

            if (!bcRel.getValue().contains(t)) { // line 5
                bcRel.getValue().add(t); // line 6

                for (TransRule transRule : bcDelta.getValue()) { // line 7: traverse delta
                    Configuration endConf = transRule.getEndConf();
                    List<String> stackElements = endConf.getStackElements();

                    if (stackElements.size() != 1) { // line 7: check the size of stacks, we only accept size 1
                        continue;
                    }

                    if (q.equals(endConf.getState()) && // line 7: check belonging relationship
                            gamma.equals(stackElements.get(0))) {

                        bcTrans.getValue().add(new Transition( // line 8
                                transRule.getStartConf().getState(),
                                transRule.getStartConf().getStackElements().get(0),
                                q_prime));

                    }
                }

                for (TransRule transRule : bcDelta_prime.getValue()) { // line 7: traverse delta_prime
                    Configuration endConf = transRule.getEndConf();
                    List<String> stackElements = endConf.getStackElements();

                    if (stackElements.size() != 1) { // line 7: check the size of stacks, we only accept size 1
                        continue;
                    }

                    if (q.equals(endConf.getState()) && // line 7: check belonging relationship
                            gamma.equals(stackElements.get(0))) {

                        bcTrans.getValue().add(new Transition( // line 8
                                transRule.getStartConf().getState(),
                                transRule.getStartConf().getStackElements().get(0),
                                q_prime));

                    }
                }

                for (TransRule transRule : bcDelta.getValue()) { // line 9: traverse delta
                    Configuration endConf = transRule.getEndConf();
                    List<String> stackElements = endConf.getStackElements();

                    if (stackElements.size() < 2) { // line 9: check the size of stacks
                        continue;
                    }

                    String gamma2 = stackElements.get(1);

                    if (q.equals(endConf.getState()) && // line 9: check belonging relationship
                            gamma.equals(endConf.getStackElements().get(0))) {

                        List<String> list = new ArrayList<>(1);
                        list.add(gamma2);

                        bcDelta_prime.getValue().add(new TransRule( // line 10
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


            }
        }


        Util.log("The size of rel", bcRel.value().size());
        for (Transition t : bcRel.getValue()) {
            Util.log("Transition", t.toString());
        }

    }

    private static List<TransRule> parseInputFile(String filename) {
        List<TransRule> ruleSet = new ArrayList<>();

        ANTLRFileStream input = null;
        try {
            input = new ANTLRFileStream(filename);

            PdsLexer lexer = new PdsLexer(null);
            lexer.setInputStream(input);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            PdsParser parser = new PdsParser(tokens);
            ParseTree tree = parser.pds();

            PdsBaseVisitor visitor = new PdsBaseVisitor(ruleSet);
            visitor.visit(tree);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ruleSet;
    }
}
