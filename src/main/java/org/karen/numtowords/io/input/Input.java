package org.karen.numtowords.io.input;

import java.util.Scanner;

public interface Input {

    Scanner getReader();

    Type getType();

    String getNextNumber();

    enum Type {
        USER, FILE, DICTIONARY;
    }

}
