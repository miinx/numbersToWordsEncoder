package org.karen.numtowords.dictionary;

import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.util.FileUtils;

import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String DICTIONARY_NOT_FOUND_MESSAGE = "Dictionary file not found: ";

    private Scanner reader;
    private FileUtils fileUtils = new FileUtils();
    private String dictionaryFileName;

    public Dictionary(String dictionaryFileName)
            throws IOException {

        this.dictionaryFileName = dictionaryFileName;
        this.reader = fileUtils.loadFile(dictionaryFileName, Type.DICTIONARY);
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
