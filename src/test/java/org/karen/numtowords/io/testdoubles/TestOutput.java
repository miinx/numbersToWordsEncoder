package org.karen.numtowords.io.testdoubles;

import org.karen.numtowords.io.output.Output;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestOutput implements Output {

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private PrintStream out;

    public TestOutput() {
        this.out = new PrintStream(buffer);
    }

    @Override
    public void write(String message) {
        this.out.print(message);
    }

    @Override
    public Type getType() {
        return Type.CAPTURED;
    }

    public String getOutput() {
        return buffer.toString();
    }


}
