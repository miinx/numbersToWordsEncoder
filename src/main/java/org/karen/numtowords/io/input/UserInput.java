package org.karen.numtowords.io.input;

import java.util.Scanner;

public class UserInput implements Input {

    private Scanner reader;

    public UserInput() {
        this.reader = new Scanner(System.in);
    }

    @Override
    public Scanner getReader() {
        return reader;
    }

    @Override
    public Type getType() {
        return Type.USER;
    }

}
