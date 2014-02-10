package org.karen.numtowords.dictionary;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.util.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String DICTIONARY_NOT_FOUND_MESSAGE = "Dictionary file not found: ";
    public static final String VALID_DICTIONARY_LINE_REGEX = "^[^\\d ]*$";

    private Scanner reader;
    private String dictionaryFileName;

    public Dictionary(String dictionaryFileName)
            throws IOException, FileNotValidException {

        FileUtils fileUtils = new FileUtils();
        FileInputStream fileInputStream = fileUtils.loadFile(dictionaryFileName, Type.DICTIONARY);

        this.dictionaryFileName = dictionaryFileName;
        this.reader = new Scanner(fileInputStream);

        fileUtils.validate(dictionaryFileName, fileInputStream, Type.DICTIONARY);
    }

    public String getDictionaryFileName() {
        return dictionaryFileName;
    }

    @Override
    public Scanner getReader() {
        return reader;
    }

    @Override
    public Type getType() {
        return Type.DICTIONARY;
    }

}