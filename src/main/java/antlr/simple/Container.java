package antlr.simple;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pds.simple.TransRule;
import pds.simple.Transition;
import scala.Tuple2;
import util.Cantor;

import java.io.IOException;
import java.util.*;

/**
 * Created by Cynric on 7/8/16.
 */
public class Container {
    public static Map<Integer, String> codeDictionary = new HashMap<>(); // 将字符串的hashcode和字符串本身做个映射
    public static Map<String, Integer> symbolDictionary = new HashMap<>(); // 将字符串的hashcode和字符串本身做个映射
    public static int count = 1;

    static {
        registerString(1, "__s__");
    }

    public int[] startConf;
    public List<int[]> ruleSet = new ArrayList<>();
    public int[] startConfArray; // [ fromState, fromStack, toState, toStack ]
    public int[][] ruleArray; // [ [fromState, fromStack, toState, toStack1, toStack2] ]

    private static void registerString(int code, String s) {
        if (!codeDictionary.containsKey(code)) {
            codeDictionary.put(code, s);
        } else {
            System.out.println("ERROR: code " + code + "already exists in codeDictionary !!!");
        }

        if (!symbolDictionary.containsKey(s)) {
            symbolDictionary.put(s, code);
        } else {
            System.out.println("ERROR: code " + code + "already exists in symbolDictionary !!!");
        }
    }

    public static int getCode(String s) {
        if (symbolDictionary.containsKey(s)) {
            return symbolDictionary.get(s);
        } else {
            count += 1;
            registerString(count, s);
            return count;
        }
    }

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

    public static String getString(int hashcode) {
        return codeDictionary.get(hashcode);
    }

    public static List<String> transfer(Collection<int[]> collection) {
        List<String> ret = new ArrayList<>();
        for (int[] t : collection) {
            ret.add(TransRule.toString(t));
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
