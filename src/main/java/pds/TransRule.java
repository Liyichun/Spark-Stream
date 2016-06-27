package pds;

import antlr.PdsParser;

import java.io.Serializable;

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


}
