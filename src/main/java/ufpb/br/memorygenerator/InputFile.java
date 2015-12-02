package main.java.ufpb.br.memorygenerator;

import java.util.HashMap;

/**
 * Created by gustavo on 01/12/15.
 */
public class InputFile {

    private int totalLines;
    private HashMap<Integer, String[]> lines;

    public InputFile() {}

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public HashMap<Integer, String[]> getLines() {
        return lines;
    }

    public void setLines(HashMap<Integer, String[]> lines) {
        this.lines = lines;
    }
}
