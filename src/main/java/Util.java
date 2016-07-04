import antlr.PdsBaseVisitor;
import antlr.PdsLexer;
import antlr.PdsParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pds.TransRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynric on 5/26/16.
 */
public class Util {

    public static void log(String content) {
        System.out.println("****************************** program output start ******************************");
        System.out.println("CONTENT: " + content);
        System.out.println("****************************** program output end ******************************");
    }

    public static void log(String title, String content) {
        System.out.println("****************************** program output start ******************************");
        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
        System.out.println("****************************** program output end ******************************");
    }


    public static void log(int i) {
        log(String.valueOf(i));
    }

    public static void log(long i) {
        log(String.valueOf(i));
    }

    public static void log(String title, int i) {
        log(title, String.valueOf(i));
    }

    public static void log(String title, long i) {
        log(title, String.valueOf(i));
    }

    public static List<TransRule> parseInputFile(String filename) {
        List<TransRule> ruleSet = new ArrayList<>();

        ANTLRFileStream input;
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
