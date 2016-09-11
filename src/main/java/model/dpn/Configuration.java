package model.dpn;

import antlr.dpn.*;
import util.Symbol;

import java.io.Serializable;

/**
 * Created by Cynric on 5/18/16.
 */
public class Configuration implements Serializable {

    public static int[] fromAntlrContext(DpnParser.ConfContext confContext) {
        String state = confContext.getChild(0).getText();
        int stateCode = Symbol.getCode(state);

        DpnParser.StackContext stackContext = (DpnParser.StackContext) confContext.getChild(1);
        DpnParser.Stack_contentContext stackElemContext = (DpnParser.Stack_contentContext) stackContext.getChild(1);

        int stackCount = stackElemContext.getChildCount();
        int[] ret = new int[1 + stackCount];
        ret[0] = stateCode;

        for (int i = 0; i < stackCount; i++) {
            String stack = stackElemContext.getChild(i).getText();
            int stackCode = Symbol.getCode(stack);

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
