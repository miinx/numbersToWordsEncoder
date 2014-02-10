package org.karen.numtowords.io.output;

public interface Output {

    void write(String message);

    Type getType();

    enum Type {
        CONSOLE, CAPTURED;
    }
}
