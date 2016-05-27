package rdd;

import org.apache.spark.AccumulableParam;
import pds.Transition;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cynric on 5/25/16.
 */
public class TransAccumulator implements AccumulableParam<Set<Transition>, Transition> {
    private Set<Transition> _set = new HashSet<Transition>();

    public Set<Transition> addAccumulator(Set<Transition> transitions, Transition transition) {
        _set.add(transition);
        return _set;
    }

    public Set<Transition> addInPlace(Set<Transition> r1, Set<Transition> r2) {
        r1.addAll(r2);
        _set = r1;
        return _set;
    }

    public Set<Transition> zero(Set<Transition> initialValue) {
        return null;
    }
}
