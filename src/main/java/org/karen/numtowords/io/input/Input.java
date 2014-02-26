package org.karen.numtowords.io.input;

import java.io.FileNotFoundException;
import java.util.Scanner;

public interface Input {

    public static final String EXIT_VALUE = "quit";

    Scanner getReader() throws FileNotFoundException;       // todo remove exc after separating dictionary

    Type getType();

    String getNextNumberMessage();

    String getNextNumber();

    void setNextNumber();

    enum Type {
        USER, FILE, DICTIONARY;
    }

}
