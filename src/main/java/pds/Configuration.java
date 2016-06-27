package pds;

import antlr.PdsParser;

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

    private static List<String> stackElementsFromContext(PdsParser.StackContext stackContext) {
        List<String> list = new ArrayList<>();

        PdsParser.Stack_contentContext stackElemContext = (PdsParser.Stack_contentContext) stackContext.getChild(1);

        for (int i = 0; i < stackElemContext.getChildCount(); i++) {
            String stackElem = stackElemContext.getChild(i).getText();

            list.add(stackElem);
        }

        return list;
    }
}
