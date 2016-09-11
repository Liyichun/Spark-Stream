package model.dpn;

import antlr.dpn.DpnParser;
import util.Symbol;

import java.io.Serializable;

/**
 * Created by Cynric on 5/18/16.
 */
public class TransRule implements Serializable {


    public static int[] toTransition(int[] transRule) {
        return new int[]{transRule[0], transRule[1], transRule[2]};
    }


    public static int[] fromTransContext(DpnParser.Trans_ruleContext ctx) {
        DpnParser.ConfContext startConf = (DpnParser.ConfContext) ctx.getChild(0);
        DpnParser.ConfContext endConf = (DpnParser.ConfContext) ctx.getChild(2);

        if (ctx.getChildCount() > 3) {
            DpnParser.ConfContext spawnConf = (DpnParser.ConfContext) ctx.getChild(4);
        }

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
        String output = Symbol.getString(transRule[0]) + " <" + Symbol.getString(transRule[1]) +
                "> --> " + Symbol.getString(transRule[2]);
        if (transRule.length == 3) {
            output += " <>";
        } else if (transRule.length == 4) {
            output += " <" + Symbol.getString(transRule[3]) + "> ";
        } else if (transRule.length == 5) {
            output += " <" + Symbol.getString(transRule[3]) + ", " + Symbol.getString(transRule[4]) + "> ";
        }
        return output;
    }
}
