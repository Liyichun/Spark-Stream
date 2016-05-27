package pds;

import antlr.PdsParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynric on 5/18/16.
 */
public class StackElement implements Serializable {
    private String omega = null;

    public StackElement(String omega) {
        this.omega = omega;
    }

    public String getOmega() {
        return omega;
    }

    public void setOmega(String omega) {
        this.omega = omega;
    }

    public static List<StackElement> fromStackContext(PdsParser.StackContext stackContext) {
        List<StackElement> list = new ArrayList<StackElement>();

        PdsParser.Stack_contentContext stackElemContext = (PdsParser.Stack_contentContext) stackContext.getChild(1);

        for (int i = 0; i < stackElemContext.getChildCount(); i++) {
            String stackElem = stackElemContext.getChild(i).getText();

            list.add(new StackElement(stackElem));
        }

        return list;

    }

}
