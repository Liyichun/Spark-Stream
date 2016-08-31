package pds.simple;

import antlr.simple.Container;

import java.io.Serializable;

/**
 * Created by Cynric on 5/24/16.
 */
public class Transition implements Serializable {
    public int startState;
    public int finalState;
    public int alphabet;
    public int[] trans;


    public Transition(int startState, int alphabet, int finalState) {
        this.startState = startState;
        this.finalState = finalState;
        this.alphabet = alphabet;
    }

    public Transition(int[] trans) {
        this.trans = trans;
    }

    public static int[][] getStartTrans(int[] startConf) {
        int[][] ret = new int[startConf.length - 1][3];
        ret[0] = new int[]{startConf[0], startConf[1], "__s1__".hashCode()};
        Container.registerString("__s1__".hashCode(), "__s1__");

        for (int i = 1; i < startConf.length - 1; i++) {
            String toState = "__s" + (i + 1) + "__";
            Container.registerString(toState.hashCode(), toState);

            ret[i] = new int[]{
                    ("__s" + i + "__").hashCode(),
                    startConf[i + 1],
                    toState.hashCode()
            };
        }
        return ret;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null || obj.getClass() != this.getClass()) {
//            return false;
//        } else {
//            Transition t = (Transition) obj;
//            return this.startState.equals(t.startState) && this.finalState.equals(t.finalState) && this.alphabet.equals(t.alphabet);
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 37 * result + startState.hashCode();
//        result = 37 * result + alphabet.hashCode();
//        result = 37 * result + finalState.hashCode();
//        return result;
//    }

//    public boolean equals(String startState, String alphabet, String finalState) {
//        return this.startState.equals(startState) && this.finalState.equals(finalState) && this.alphabet.equals(alphabet);
//    }
//
//    public boolean equals(String startState, String alphabet) {
//        return this.startState.equals(startState) && this.alphabet.equals(alphabet);
//    }

    public static String toString(int[] t) {
        return String.format("%s -[%s]-> %s", Container.getString(t[0]), Container.getString(t[1]), Container.getString(t[2]));
    }
}
