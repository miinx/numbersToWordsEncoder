package org.karen.numtowords;

import org.karen.numtowords.app.Engine;
import org.karen.numtowords.exception.FileNotValidException;

import java.io.IOException;

public class NumberToWords {

    private Engine engine;

    public static void main(String[] args)
            throws IOException, FileNotValidException {

        NumberToWords numberToWords = new NumberToWords();
        numberToWords.run(args);
    }

    public void run(String[] args)
            throws IOException, FileNotValidException {

        this.engine = new Engine();

        engine.configure(args);
        engine.writeWelcomeMessage();
        engine.processNumbers();
    }

    public Engine getEngine() {
        return engine;
    }

}
