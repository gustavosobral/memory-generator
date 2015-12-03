package main.java.ufpb.br.memorygenerator;

import java.io.IOException;

/**
 * Created by gustavo on 28/11/15.
 */
public class Main {

    public static void main(String[] args) {

	    if(args.length == 3) {
            MemoryGenerator memoryGen = new MemoryGenerator();

            try {
                Instructions instructions = memoryGen.loadInstructions(args[0]);
                InputFile inputFile = memoryGen.readInput(args[1]);
                memoryGen.writeOutput(args[2], inputFile, instructions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("USAGE: java -jar arq2_ufpb.jar <instructions> <input> <output>");
        }
    }
}
