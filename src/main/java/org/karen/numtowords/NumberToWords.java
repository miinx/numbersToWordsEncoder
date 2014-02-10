package org.karen.numtowords;

import org.karen.numtowords.app.Engine;

public class NumberToWords {

    private Engine engine;

    public static void main(String[] args) {
        NumberToWords numberToWords = new NumberToWords().createInstance();
        numberToWords.run(args);
    }

    public NumberToWords createInstance() {
        return new NumberToWords();
    }

    public void run(String[] args) {
        this.engine = new Engine();

        engine.configure(args);
        engine.writeWelcomeMessage();
    }

    public Engine getEngine() {
        return engine;
    }

}
