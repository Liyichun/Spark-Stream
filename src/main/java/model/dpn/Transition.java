package model.dpn;

import util.Symbol;

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
        int lastToStateCode = Symbol.getCode("__s__");
        ret[0] = new int[]{startConf[0], startConf[1], lastToStateCode};

        for (int i = 1; i < startConf.length - 1; i++) {
            String toState = "__s" + (i + 1) + "__";
            int toStateCode = Symbol.getCode(toState);

            ret[i] = new int[]{
                    lastToStateCode,
                    startConf[i + 1],
                    toStateCode
            };
            lastToStateCode = toStateCode;
        }
        return ret;
    }

//    public static int[] fromContext() {
//
//    }

    public static String toString(int[] t) {
        return String.format("%s -[%s]-> %s", Symbol.getString(t[0]), Symbol.getString(t[1]), Symbol.getString(t[2]));
    }
}
