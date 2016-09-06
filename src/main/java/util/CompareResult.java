package util;

import pds.Transition;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cynric on 8/30/16.
 */
public class CompareResult {
    static Pattern transPattern = Pattern.compile("(\\w*) -\\[(\\w*)\\]-> (\\w*)");

    public static void main(String[] args) {
        compare("plot.txt");

    }

    public static void compare(String filename) {
        String mopedFile = "moped_" + filename;

        Set<Transition> sparkSet = getTransFromFile("output/" + filename);
        Set<Transition> mopedSet = getTransFromFile("output/" + mopedFile);
        Set<Transition> matchSet = new HashSet<>();

        Iterator sparkSetIter = sparkSet.iterator();
        while (sparkSetIter.hasNext()) {
            Transition t = (Transition) sparkSetIter.next();
            if (mopedSet.contains(t)) {
                sparkSetIter.remove();
                mopedSet.remove(t);
                matchSet.add(t);
            }
        }


        if (!sparkSet.isEmpty()) {
            Util.log("remaining item in spark set", sparkSet);
        }
        if (!mopedSet.isEmpty()) {
            Util.log("remaining item in moped set", mopedSet);
        }

    }

    public static Set<Transition> getTransFromFile(String filename) {
        Set<Transition> set = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.lines().forEach(line -> {
                Matcher matcher = transPattern.matcher(line.trim());
                if (matcher.matches()) {
                    set.add(new Transition(matcher.group(1), matcher.group(2), matcher.group(3)));
                }


            });

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return set;
    }
}
