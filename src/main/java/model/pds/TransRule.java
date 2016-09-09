package model.pds;

import antlr.PdsParser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cynric on 5/18/16.
 */
public class TransRule implements Serializable {
    Configuration startConf;
    Configuration endConf;


    public TransRule(Configuration startConf, Configuration endConf) {
        this.startConf = startConf;
        this.endConf = endConf;
    }

    public static TransRule fromTransContext(PdsParser.Trans_ruleContext ctx) {
        PdsParser.ConfContext startConf = (PdsParser.ConfContext) ctx.getChild(0);
        PdsParser.ConfContext endConf = (PdsParser.ConfContext) ctx.getChild(2);

        return new TransRule(Configuration.fromAntlrContext(startConf),
                Configuration.fromAntlrContext(endConf));

    }

    public Transition toTransition() {
        String startState = startConf.getState();
        String stackElement = startConf.stackElements.get(0);
        String endState = endConf.getState();

        return new Transition(startState, stackElement, endState);
    }

    public Configuration getEndConf() {
        return endConf;
    }

    public void setEndConf(Configuration endConf) {
        this.endConf = endConf;
    }

    public Configuration getStartConf() {
        return startConf;
    }

    public void setStartConf(Configuration startConf) {
        this.startConf = startConf;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            TransRule tr = (TransRule) obj;
            return this.getStartConf().equals(tr.getStartConf()) && this.getEndConf().equals(tr.getEndConf());
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.getStartConf().hashCode();
        result = 37 * result + this.getEndConf().hashCode();
        return result;
    }

    @Override
    public String toString() {
        List endStack = this.endConf.getStackElements();
        String output = this.startConf.getState() + " <" + this.startConf.getStackElements().get(0).toString() +
                "> --> " + this.endConf.getState();
        if (endStack.size() == 0) {
            output += " <>";
            return output;
        } else if (endStack.size() == 1) {
            output += " <" + endStack.get(0).toString() + "> ";
            return output;
        } else {
            output += " <" + endStack.get(0).toString() + ", " + endStack.get(1).toString() + "> ";
            return output;
        }

    }
}
