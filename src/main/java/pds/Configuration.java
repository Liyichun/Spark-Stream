package pds;

import antlr.PdsParser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cynric on 5/18/16.
 */
public class Configuration implements Serializable{
    State state;
    List<StackElement> stackElements;

    public Configuration(State state, StackElement stackElement1, StackElement stackElement2) {
        this.state = state;
        this.stackElements.add(stackElement1);
        this.stackElements.add(stackElement2);
    }

    public Configuration(State state, List<StackElement> stackElements) {
        this.state = state;
        this.stackElements = stackElements;
    }

    public static Configuration fromAntlrContext(PdsParser.ConfContext confContext) {
        State s = new State(confContext.getChild(0).getText());
        List<StackElement> stackElements = StackElement.fromStackContext((PdsParser.StackContext) confContext.getChild(1));

        return new Configuration(s, stackElements);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<StackElement> getStackElements() {
        return stackElements;
    }

    public void setStackElements(List<StackElement> stackElements) {
        this.stackElements = stackElements;
    }
}
