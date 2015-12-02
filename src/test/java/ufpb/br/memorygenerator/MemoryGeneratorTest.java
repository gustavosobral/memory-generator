package test.java.ufpb.br.memorygenerator;

import main.java.ufpb.br.memorygenerator.Instructions;
import main.java.ufpb.br.memorygenerator.MemoryGenerator;
import main.java.ufpb.br.memorygenerator.InputFile;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by gustavo on 28/11/15.
 */
public class MemoryGeneratorTest {

    @Test
    public void missingInputFile() {
        MemoryGenerator memoryGen = new MemoryGenerator();
        IOException exception = null;
        try {
            memoryGen.readInput("some.file");
        } catch (IOException e) {
            exception = e;
        }
        Assert.assertNotNull("Exception was not thrown", exception);
        Assert.assertEquals("File is invalid or is not readable!", exception.getMessage());
    }

    @Test
    public void missingInstructionsFile() {
        MemoryGenerator memoryGen = new MemoryGenerator();
        IOException exception = null;
        URL urlToInstructions = this.getClass().getResource("/main/resources/" + "instructionss");
        try {
            memoryGen.loadInstructions(urlToInstructions);
        } catch (IOException e) {
            exception = e;
        }
        Assert.assertNotNull("Exception was not thrown", exception);
        Assert.assertEquals("Invalid instructions file!", exception.getMessage());
    }

    @Test
    public void invalidOutputFile() {
        MemoryGenerator memoryGen = new MemoryGenerator();
        IOException exception = null;
        try {
            memoryGen.writeOutput("./", null, null);
        } catch (IOException e) {
            exception = e;
        }
        Assert.assertNotNull("Exception was not thrown", exception);
        Assert.assertEquals("FileNotFoundException!", exception.getMessage());
    }

    @Test
    public void readValidInput() {
        MemoryGenerator memoryGen = new MemoryGenerator();
        InputFile inputFile = new InputFile();

        try {
            URL input = this.getClass().getResource("/main/resources/" + "input1");
            File f = new File(input.toURI());
            inputFile = memoryGen.readInput(f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("DUP", inputFile.getLines().get(1)[0]);
        Assert.assertEquals("IN", inputFile.getLines().get(2)[0]);
        Assert.assertEquals("GOTO", inputFile.getLines().get(3)[0]);
        Assert.assertEquals("offset", inputFile.getLines().get(3)[1]);
        Assert.assertEquals("IF_ICMPEQ", inputFile.getLines().get(4)[0]);
        Assert.assertEquals("offset", inputFile.getLines().get(4)[1]);
        Assert.assertEquals(4, inputFile.getTotalLines());
    }

    @Test
    public void readValidInstructions() {
        MemoryGenerator memoryGen = new MemoryGenerator();
        IOException exception = null;
        URL urlToInstructions = this.getClass().getResource("/main/resources/" + "instructions");
        Instructions instructions = new Instructions();
        try {
            instructions = memoryGen.loadInstructions(urlToInstructions);
        } catch (IOException e) {
            exception = e;
        }
        Assert.assertEquals("000001011000001101010000010010000100", instructions.getInstructions().get("DUP").get(0));
        Assert.assertEquals("000000010000000101000000000101000111", instructions.getInstructions().get("DUP").get(1));
        Assert.assertEquals("010001111000001111000100000000001000", instructions.getInstructions().get("IN").get(1));
        Assert.assertEquals("010010001000001101010000010010000100", instructions.getInstructions().get("IN").get(3));
        Assert.assertEquals("000001101000000000000000000000000000", instructions.getInstructions().get("POP").get(1));
    }

    @Test
    public void writeOutput() {
        MemoryGenerator memoryGen = new MemoryGenerator();
        List<String> output = new LinkedList<String>();

        try {
            URL urlToInstructions = this.getClass().getResource("/main/resources/" + "instructions");
            URL in = this.getClass().getResource("/main/resources/" + "input2");
            File inF = new File(in.toURI());
            URL out = this.getClass().getResource("/main/resources/" + "output2");
            File outF = new File(out.toURI());

            InputFile inputFile = memoryGen.readInput(inF.getAbsolutePath());
            Instructions instructions = memoryGen.loadInstructions(urlToInstructions);
            memoryGen.writeOutput(outF.getAbsolutePath(), inputFile, instructions);

            for (String line : Files.readAllLines(Paths.get(outF.getAbsolutePath()), StandardCharsets.UTF_8)) {
                output.add(line);
            }

            Assert.assertNotEquals(0, output.size());
            Assert.assertEquals("010001110000000100101100000000000000", output.get(0));
            Assert.assertEquals("010010001000001101010000010010000100", output.get(3));
            Assert.assertEquals("000000001000000101000010000001000000", output.get(4));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
