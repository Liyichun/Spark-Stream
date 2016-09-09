package model.pds.simple;

import antlr.simple.*;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Cynric on 5/18/16.
 */
public class TransRule implements Serializable {
    public int fromState;
    public int fromStack;
    public int toState;
    public int toStack1 = -1;
    public int toStack2 = -1;


    public static int[] toTransition(int[] transRule) {
        return new int[]{transRule[0], transRule[1], transRule[2]};
    }


    public static int[] fromTransContext(antlr.simple.PdsParser.Trans_ruleContext ctx) {
        PdsParser.ConfContext startConf = (PdsParser.ConfContext) ctx.getChild(0);
        PdsParser.ConfContext endConf = (PdsParser.ConfContext) ctx.getChild(2);

        int[] fromConf = Configuration.fromAntlrContext(startConf);
        int[] toConf = Configuration.fromAntlrContext(endConf);

        int[] ret = new int[fromConf.length + toConf.length];
        System.arraycopy(fromConf, 0, ret, 0, fromConf.length);
        System.arraycopy(toConf, 0, ret, fromConf.length, toConf.length);
        return ret;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null || obj.getClass() != this.getClass()) {
//            return false;
//        } else {
//            TransRule tr = (TransRule) obj;
//            return this.getStartConf().equals(tr.getStartConf()) && this.getEndConf().equals(tr.getEndConf());
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 37 * result + this.getStartConf().hashCode();
//        result = 37 * result + this.getEndConf().hashCode();
//        return result;
//    }

    public static String toString(int[] transRule) {
        String output = Container.getString(transRule[0]) + " <" + Container.getString(transRule[1]) +
                "> --> " + Container.getString(transRule[2]);
        if (transRule.length == 3) {
            output += " <>";
        } else if (transRule.length == 4) {
            output += " <" + Container.getString(transRule[3]) + "> ";
        } else if (transRule.length == 5) {
            output += " <" + Container.getString(transRule[3]) + ", " + Container.getString(transRule[4]) + "> ";
        }
        return output;
    }
}
