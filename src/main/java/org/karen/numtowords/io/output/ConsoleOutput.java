package org.karen.numtowords.io.output;

import java.io.PrintStream;
import java.util.List;

public class ConsoleOutput implements Output {

    private static final String RESULT_START = "Word matches for the number '%s': ";
    private static final String RESULT_SUMMARY = "Total matches: %d";

    private PrintStream out;

    public ConsoleOutput() {
        this.out = new PrintStream(System.out);
    }

    @Override
    public void write(String message) {
        out.print(message + "\n");
        out.flush();
    }

    @Override
    public void writeEncodingResults(String number, List<String> wordMatches) {
        write(String.format(RESULT_START, number));
        for (String word : wordMatches) {
            write(word);
        }
        write(String.format(RESULT_SUMMARY, wordMatches.size()));
    }

}
