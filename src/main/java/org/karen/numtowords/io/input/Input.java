package org.karen.numtowords.io.input;

import java.util.Scanner;

public interface Input {

    Scanner getReader();

    Type getType();

    enum Type {
        USER, FILE, DICTIONARY;
    }

}
