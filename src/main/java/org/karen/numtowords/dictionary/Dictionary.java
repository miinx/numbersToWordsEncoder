package org.karen.numtowords.dictionary;

import org.karen.numtowords.io.input.Input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";

    private String dictionaryFileName;
    private FileInputStream dictionaryStream;
    private Scanner dictionaryReader;

    public Dictionary(String dictionaryFileName)
            throws FileNotFoundException {
        this.dictionaryFileName = dictionaryFileName;
        loadDictionary();
    }

    public String getDictionaryFileName() {
        return dictionaryFileName;
    }

    public FileInputStream getDictionaryStream() {
        return dictionaryStream;
    }

    @Override
    public Scanner getReader() {
        return dictionaryReader;
    }

    @Override
    public Type getType() {
        return Type.DICTIONARY;
    }

    private void loadDictionary()
            throws FileNotFoundException {

        dictionaryStream = new FileInputStream(dictionaryFileName);
        dictionaryReader = new Scanner(dictionaryStream);
        // todo: exceptions
    }

}
