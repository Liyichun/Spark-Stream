package simplepds;

import antlr.PdsParser;
import scala.tools.cmd.gen.AnyVals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynric on 5/18/16.
 */
public class Configuration implements Serializable {
    String state;
    List<String> stackElements;

    public Configuration(String state, String stackElement1, String stackElement2) {
        this.state = state;
        this.stackElements.add(stackElement1);
        this.stackElements.add(stackElement2);
    }

    public Configuration(String state, List<String> stackElements) {
        this.state = state;
        this.stackElements = stackElements;
    }

    public static Configuration fromAntlrContext(PdsParser.ConfContext confContext) {
        String state = confContext.getChild(0).getText();
        List<String> stackElements = stackElementsFromContext((PdsParser.StackContext) confContext.getChild(1));

        return new Configuration(state, stackElements);
    }

    private static List<String> stackElementsFromContext(PdsParser.StackContext stackContext) {
        List<String> list = new ArrayList<>();

        PdsParser.Stack_contentContext stackElemContext = (PdsParser.Stack_contentContext) stackContext.getChild(1);

        for (int i = 0; i < stackElemContext.getChildCount(); i++) {
            String stackElem = stackElemContext.getChild(i).getText();

            list.add(stackElem);
        }

        return list;
    }

    public List<String> getStackElements() {
        return stackElements;
    }

    public void setStackElements(List<String> stackElements) {
        this.stackElements = stackElements;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            Configuration conf = (Configuration) obj;
            return this.state.equals(conf.state) && this.stackElements.equals(conf.stackElements);
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + state.hashCode();
        result = 37 * result + stackElements.hashCode();
//        System.out.println(result);
        return result;
    }
}
