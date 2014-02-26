package org.karen.numtowords.dictionary;

import org.karen.numtowords.validation.DataType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface Dictionary {

    Scanner getReader() throws FileNotFoundException;

    DataType getType();

    File getDictionaryFile();

}
