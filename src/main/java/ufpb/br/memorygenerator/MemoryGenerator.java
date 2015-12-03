package main.java.ufpb.br.memorygenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gustavo on 28/11/15.
 */
public class MemoryGenerator {

    public InputFile readInput(String path) throws IOException {
        Path inputPath = Paths.get(path);
        HashMap<Integer, String[]> readFile = new HashMap<Integer, String[]>();

        if(Files.isRegularFile(inputPath) && Files.isReadable(inputPath)){
            Integer lineCounter = 1;

            for (String line : Files.readAllLines(inputPath, StandardCharsets.UTF_8)) {
                String[] strings = line.split("\\s+");
                readFile.put(lineCounter, strings);
                lineCounter++;
            }

            InputFile result = new InputFile();
            result.setTotalLines(lineCounter - 1);
            result.setLines(readFile);

            return result;
        } else {
            throw new IOException("File is invalid or is not readable!");
        }
    }

    public Instructions loadInstructions(String path) throws IOException {
        Path inputPath = Paths.get(path);
        HashMap<String, List<String>> instructions = new HashMap<String, List<String>>();

        if(Files.isRegularFile(inputPath) && Files.isReadable(inputPath)) {
            try (BufferedReader br = new BufferedReader(new FileReader(inputPath.toFile()))) {
                String readLine;
                String[] splittedStrings;
                List<String> bits;
                int instructionLines;

                while ((readLine = br.readLine()) != null) {
                    bits = new LinkedList<String>();
                    splittedStrings = readLine.split("\\s+");
                    instructionLines = Integer.parseInt(splittedStrings[splittedStrings.length - 1]);

                    for (int i = 0; i < instructionLines; i++) {
                        readLine = br.readLine();
                        bits.add(readLine);
                    }
                    readLine = br.readLine();
                    instructions.put(splittedStrings[0], bits);
                }
            }

            Instructions result = new Instructions();
            result.setInstructions(instructions);

            return result;
        } else {
            throw new IOException("Invalid instructions file!");
        }
    }

    public void writeOutput(String path, InputFile input, Instructions instructions) throws IOException {
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");

            for(int i = 1; i <= input.getTotalLines(); i++){
                List<String> bits = instructions.getInstructions().get(input.getLines().get(i)[0]);
                for(int j = 0; j < bits.size(); j++){
                    writer.println(bits.get(j));
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new IOException("FileNotFoundException!");
        } catch (UnsupportedEncodingException e) {
            throw new IOException("UnsupportedEncodingException!");
        }
    }
}
