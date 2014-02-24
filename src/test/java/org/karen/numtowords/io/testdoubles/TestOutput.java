package org.karen.numtowords.io.testdoubles;

import org.karen.numtowords.io.output.Output;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class TestOutput implements Output {

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private PrintStream out;

    public TestOutput() {
        out = new PrintStream(buffer);
    }

    @Override
    public void write(String message) {
        out.print(message);
    }

    @Override
    public void writeEncodingResults(String number, List<String> wordMatches) {
        write("result from " + number);
    }

    public String getOutput() {
        return buffer.toString();
    }


}
