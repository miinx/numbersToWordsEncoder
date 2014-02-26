package org.karen.numtowords.io.input;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.validation.DataType;

import java.io.IOException;
import java.util.Scanner;

public interface Input {

    public static final String EXIT_VALUE = "quit";

    Scanner getReader();

    DataType getType();

    String getNextNumberMessage();

    String getNextNumber();

    void setNextNumber() throws IOException, FileNotValidException;

}
