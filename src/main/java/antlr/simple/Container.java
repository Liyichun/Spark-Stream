package antlr.simple;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import model.pds.simple.TransRule;
import model.pds.simple.Transition;
import scala.Tuple2;
import scala.Tuple3;
import util.Cantor;

import java.io.IOException;
import java.util.*;

import static util.Symbol.codeDictionary;

/**
 * Created by Cynric on 7/8/16.
 */
public class Container {
    public int[] startConf;
    public List<int[]> ruleSet = new ArrayList<>();
    public int[] startConfArray; // [ fromState, fromStack, toState, toStack ]
    public int[][] ruleArray; // [ [fromState, fromStack, toState, toStack1, toStack2] ]

    public static Container parseInputFile(String filename) {
        Container container = new Container();

        ANTLRFileStream input;
        try {
            input = new ANTLRFileStream(filename);

            PdsLexer lexer = new PdsLexer(null);
            lexer.setInputStream(input);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            PdsParser parser = new PdsParser(tokens);
            ParseTree tree = parser.pds();

            PdsBaseVisitor visitor = new PdsBaseVisitor(container);
            visitor.visit(tree);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return container;
    }

    public static List<String> transfer(Collection<Tuple3<Integer, Integer, Integer>> collection) {
        List<String> ret = new ArrayList<>();
        for (Tuple3 t : collection) {
            int[] a = new int[3];
            a[0] = (int) t._1();
            a[1] = (int) t._2();
            a[2] = (int) t._3();
            ret.add(Transition.toString(a));
        }
        return ret;
    }

    public static List<String> transferCantor(Map<Integer, Set<Integer>> map) {
        List<String> ret = new ArrayList<>();
        for (int sig : map.keySet()) {
            int[] pair = Cantor.dePair(sig);
            int[] trans = new int[3];
            trans[0] = pair[0];
            trans[1] = pair[1];
            for (int q : map.get(sig)) {
                trans[2] = q;
                ret.add(Transition.toString(trans));
            }
        }
        return ret;
    }

    public static List<String> transfer(Map<Tuple2, Set<Integer>> map) {
        List<String> ret = new ArrayList<>();
        for (Tuple2 tuple : map.keySet()) {
            int[] trans = new int[3];
            trans[0] = (int) tuple._1;
            trans[1] = (int) tuple._2;
            for (int q : map.get(tuple)) {
                trans[2] = q;
                ret.add(Transition.toString(trans));
            }
        }
        return ret;
    }

    public void printRuleSet() {
        System.out.println("------ START PRINT INPUT ------");
        for (int[] transRule : this.ruleSet) {
            System.out.println(TransRule.toString(transRule));
        }
        System.out.println("------ END PRINT INPUT ------");
    }
}
