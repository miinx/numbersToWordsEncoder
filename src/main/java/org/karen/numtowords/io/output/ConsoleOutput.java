package org.karen.numtowords.io.output;

import java.io.PrintStream;

public class ConsoleOutput implements Output {

    private PrintStream out;

    public ConsoleOutput() {
        this.out = new PrintStream(System.out);
    }

    @Override
    public void write(String message) {
        out.print(message);
    }

}
