package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cynric on 9/11/16.
 */
public class Symbol {
    public static Map<Integer, String> codeDictionary = new HashMap<>(); // 将字符串的hashcode和字符串本身做个映射
    public static Map<String, Integer> symbolDictionary = new HashMap<>(); // 将字符串的hashcode和字符串本身做个映射
    public static int count = 1;

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

    public static String getString(int hashcode) {
        return codeDictionary.get(hashcode);
    }
}
