package model.pds.simple;

import scala.Tuple2;
import scala.Tuple3;
import util.Cantor;
import util.Symbol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Tuple2<Integer, Integer>> getStartTransList(int[] startConf) {
        List<Tuple2<Integer, Integer>> ret = new ArrayList<>();
        int lastToStateCode = Symbol.getCode("__s__");

        ret.add(new Tuple2<>(Cantor.codePair(startConf[0], startConf[1]), lastToStateCode));

        for (int i = 1; i < startConf.length - 1; i++) {
            String toState = "__s" + (i + 1) + "__";
            int toStateCode = Symbol.getCode(toState);

            ret.add(new Tuple2<>(Cantor.codePair(lastToStateCode, startConf[i + 1]), toStateCode));
            lastToStateCode = toStateCode;
        }
        return ret;
    }

    public static List<Tuple3<Integer, Integer, Integer>> getStartTransList(int[] startConf, boolean cantor) {
        List<Tuple3<Integer, Integer, Integer>> ret = new ArrayList<>();
        int lastToStateCode = Symbol.getCode("__s1__");

        ret.add(new Tuple3<>(startConf[0], startConf[1], lastToStateCode));

        for (int i = 1; i < startConf.length - 1; i++) {
            String toState = "__s" + (i + 1) + "__";
            int toStateCode = Symbol.getCode(toState);

            ret.add(new Tuple3<>(lastToStateCode, startConf[i + 1], toStateCode));
            lastToStateCode = toStateCode;
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
        return String.format("%s -[%s]-> %s", Symbol.getString(t[0]), Symbol.getString(t[1]), Symbol.getString(t[2]));
    }

    public static String toString(Tuple3<Integer, Integer, Integer> t) {
        return String.format("%s -[%s]-> %s", Symbol.getString(t._1()), Symbol.getString(t._2()), Symbol.getString(t._3()));
    }
}
