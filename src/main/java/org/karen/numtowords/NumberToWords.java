package org.karen.numtowords;

import org.karen.numtowords.app.Engine;

import java.io.IOException;

public class NumberToWords {

    private Engine engine;

    public static void main(String[] args)
            throws IOException {

        NumberToWords numberToWords = new NumberToWords().createInstance();
        numberToWords.run(args);
    }

    public NumberToWords createInstance() {
        return new NumberToWords();
    }

    public void run(String[] args)
            throws IOException {

        this.engine = new Engine();

        engine.configure(args);
        engine.writeWelcomeMessage();
    }

    public Engine getEngine() {
        return engine;
    }

}
