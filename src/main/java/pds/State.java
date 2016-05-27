package pds;

import java.io.Serializable;

/**
 * Created by Cynric on 5/18/16.
 */
public class State implements Serializable {
    private String name;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
