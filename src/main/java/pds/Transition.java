package pds;

import java.io.Serializable;

/**
 * Created by Cynric on 5/24/16.
 */
public class Transition implements Serializable {
    String startState;
    String finalState;
    String alphabet;

    public Transition() {
        this.startState = null;
        this.finalState = null;
        this.alphabet = null;
    }

    public Transition(String startState, String finalState, String alphabet) {
        this.startState = startState;
        this.finalState = finalState;
        this.alphabet = alphabet;
    }
}
