package util;

import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cynric on 9/11/16.
 */
public class Symbol {
    public static Map<Integer, String> codeDictionary = new HashMap<>(); // 将字符串的hashcode和字符串本身做个映射
    public static Map<String, Integer> symbolDictionary = new HashMap<>(); // 将字符串的hashcode和字符串本身做个映射
    public static int count = 1;
    public static Map<Tuple2<Integer, Integer>, Integer> postStateMap = new HashMap<>(); // when computing post*, there will be new combine state generated, we store those new state in this map.

    static {
        registerString(1, "__s__");
    }

    private static void registerString(int code, String s) {
        if (!codeDictionary.containsKey(code)) {
            codeDictionary.put(code, s);
        } else {
            System.out.println("ERROR: code " + code + "already exists in codeDictionary !!!");
        }

        if (!symbolDictionary.containsKey(s)) {
            symbolDictionary.put(s, code);
        } else {
            System.out.println("ERROR: code " + code + "already exists in symbolDictionary !!!");
        }
    }

    public static int getCode(String s) {
        if (symbolDictionary.containsKey(s)) {
            return symbolDictionary.get(s);
        } else {
            count += 1;
            registerString(count, s);
            return count;
        }
    }

    public static int getCodeForPostState(int p_prime, int gamma1) {
        String s_p_prime = getString(p_prime);
        String s_gamma1 = getString(gamma1);
        Tuple2<Integer, Integer> tuple = new Tuple2<>(p_prime, gamma1);

        if (postStateMap.containsKey(tuple)) {
            return postStateMap.get(tuple);
        } else {
            String combine = String.format("^%s~%s^", s_p_prime, s_gamma1);
            int code = getCode(combine);
            return code;
        }
    }

    public static String getString(int hashcode) {
        return codeDictionary.get(hashcode);
    }
}
