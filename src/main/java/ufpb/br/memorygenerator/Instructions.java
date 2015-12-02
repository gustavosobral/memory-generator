package main.java.ufpb.br.memorygenerator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gustavo on 01/12/15.
 */
public class Instructions {

    HashMap<String, List<String>> instructions;

    public Instructions() {}

    public HashMap<String, List<String>> getInstructions() {
        return instructions;
    }

    public void setInstructions(HashMap<String, List<String>> instructions) {
        this.instructions = instructions;
    }
}
