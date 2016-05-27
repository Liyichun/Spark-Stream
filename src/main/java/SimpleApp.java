import antlr.PdsBaseVisitor;
import antlr.PdsLexer;
import antlr.PdsParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.broadcast.Broadcast;
import pds.TransRule;
import pds.Transition;

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

        JavaRDD<TransRule> distData = sc.parallelize(ruleSet);

        Broadcast<Set<Transition>> bcTrans = sc.broadcast(new HashSet<>());

        distData.foreach(transRule -> {
            // find all <p, r> -> <p', e>
            if (transRule.getEndConf().getStackElements().size() == 0) {
                Transition transition = transRule.toTransition();
                bcTrans.getValue().add(transition);
            }
        });


        Util.log(bcTrans.value().size());


    }

    private static List parseInputFile(String filename) {
        List<TransRule> ruleSet = new ArrayList<TransRule>();

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
