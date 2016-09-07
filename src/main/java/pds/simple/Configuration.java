package pds.simple;

import antlr.simple.Container;
import antlr.simple.PdsParser;

import java.io.Serializable;

/**
 * Created by Cynric on 5/18/16.
 */
public class Configuration implements Serializable {
    public int state;
    public int stack1;
    public int stack2;


    public static int[] fromAntlrContext(PdsParser.ConfContext confContext) {
        String state = confContext.getChild(0).getText();
        int stateCode = Container.getCode(state);

        PdsParser.StackContext stackContext = (PdsParser.StackContext) confContext.getChild(1);
        PdsParser.Stack_contentContext stackElemContext = (PdsParser.Stack_contentContext) stackContext.getChild(1);

        int stackCount = stackElemContext.getChildCount();
        int[] ret = new int[1 + stackCount];
        ret[0] = stateCode;

        for (int i = 0; i < stackCount; i++) {
            String stack = stackElemContext.getChild(i).getText();
            int stackCode = Container.getCode(stack);

            ret[i + 1] = stackCode;
        }
        return ret;
    }


//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null || obj.getClass() != this.getClass()) {
//            return false;
//        } else {
//            Configuration conf = (Configuration) obj;
//            return this.state.equals(conf.state) && this.stackElements.equals(conf.stackElements);
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 37 * result + state.hashCode();
//        result = 37 * result + stackElements.hashCode();
////        System.out.println(result);
//        return result;
//    }
}
