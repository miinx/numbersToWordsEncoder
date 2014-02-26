package org.karen.numtowords.io.input;

import org.karen.numtowords.exception.FileNotValidException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public interface Input {

    public static final String EXIT_VALUE = "quit";

    Scanner getReader() throws FileNotFoundException;       // todo remove exc after separating dictionary

    Type getType();

    String getNextNumberMessage();

    String getNextNumber();

    void setNextNumber() throws IOException, FileNotValidException;

    enum Type {
        USER, FILE, DICTIONARY;
    }

}
