import antlr.PdsBaseVisitor;
import antlr.PdsLexer;
import antlr.PdsParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import pds.TransRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynric on 5/17/16.
 */
public class SimpleApp {
    static List<TransRule> ruleSet = new ArrayList<TransRule>();

    public static void main(String[] args) {

        parseInputFile("example/plot.pds");

        System.out.println(ruleSet.size());
//        String logFile = "/Users/cynric/Downloads/spark-1.6.1/README.md"; // Should be some file on your system
//        SparkConf conf = new SparkConf().setAppName("Simple Application");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> logData = sc.textFile(logFile).cache();
//
//        long numAs = logData.filter(new Function<String, Boolean>() {
//            public Boolean call(String s) {
//                return s.contains("a");
//            }
//        }).count();
//
//        long numBs = logData.filter(new Function<String, Boolean>() {
//            public Boolean call(String s) {
//                return s.contains("b");
//            }
//        }).count();
//
//        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    }

    private static void parseInputFile(String filename) {
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
    }
}
