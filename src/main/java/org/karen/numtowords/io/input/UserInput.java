package org.karen.numtowords.io.input;

import org.karen.numtowords.validation.DataType;

import java.util.Scanner;

public class UserInput implements Input {

    private static final String NEXT_NUMBER_MESSAGE = String.format("\nEnter a number to encode, or type '%s' to exit:", EXIT_VALUE);

    private Scanner reader;
    private String nextNumber;

    public static UserInput load() {
        return new UserInput();
    }

    private UserInput() {
        this.reader = new Scanner(System.in);
    }


    @Override
    public Scanner getReader() {
        return reader;
    }

    @Override
    public DataType getType() {
        return DataType.USER;
    }

    @Override
    public String getNextNumberMessage() {
        return NEXT_NUMBER_MESSAGE;
    }

    @Override
    public String getNextNumber() {
        return nextNumber;
    }

    @Override
    public void setNextNumber() {
        nextNumber = reader.nextLine();
    }
}
