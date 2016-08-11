import antlr.Container;
import antlr.PdsBaseVisitor;
import antlr.PdsLexer;
import antlr.PdsParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.Collection;


/**
 * Created by Cynric on 5/26/16.
 */
public class Util {

    public static void logStart() {
        System.out.println("****************************** program output start ****************************");
    }

    public static void logEnd() {
        System.out.println("****************************** program output end ******************************");
    }

    public static void log(String content) {

        System.out.println("VALUE: " + content);

    }

    public static void log(String title, String content) {
        System.out.println("TITLE: " + title);
        System.out.println("VALUE: " + content);
    }

    public static <T> void log(String title, Collection<T> iter) {
        System.out.println("TITLE: " + title);

        StringBuilder contentBuilder = new StringBuilder("");
        for (T t : iter) {
            contentBuilder.append(t.toString()).append(", ");
        }

        System.out.println("VALUE: " + contentBuilder.toString());
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
}
