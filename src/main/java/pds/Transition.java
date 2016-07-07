package pds;

import java.io.Serializable;

/**
 * Created by Cynric on 5/24/16.
 */
public class Transition implements Serializable {
    private String startState;
    private String finalState;
    private String alphabet;

    public Transition() {
        this.startState = null;
        this.finalState = null;
        this.alphabet = null;
    }

    public Transition(String startState, String alphabet, String finalState) {
        this.startState = startState;
        this.finalState = finalState;
        this.alphabet = alphabet;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            Transition t = (Transition) obj;
            return this.startState.equals(t.startState) && this.finalState.equals(t.finalState) && this.alphabet.equals(t.alphabet);
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + startState.hashCode();
        result = 37 * result + alphabet.hashCode();
        result = 37 * result + finalState.hashCode();
        return result;
    }

    public boolean equals(String startState, String alphabet, String finalState) {
        return this.startState.equals(startState) && this.finalState.equals(finalState) && this.alphabet.equals(alphabet);
    }

    public boolean equals(String startState, String alphabet) {
        return this.startState.equals(startState) && this.alphabet.equals(alphabet);
    }

    public String toString() {
        return startState + "[" + alphabet + "]" + " -> " + finalState;
    }
}
