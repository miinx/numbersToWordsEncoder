package org.karen.numtowords.dictionary;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.util.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String VALID_DICTIONARY_LINE_REGEX = "^[^\\d ]*$";

    private Scanner reader;
    private FileUtils fileUtils = new FileUtils();
    private FileInputStream fileInputStream;
    private String dictionaryFileName;

    public static Dictionary load(String userDictionary)
            throws IOException, FileNotValidException {

        return new Dictionary(userDictionary);
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

    private void validateDictionaryFile(String dictionaryFileName)
            throws IOException, FileNotValidException {
        
        fileUtils.validate(dictionaryFileName, fileInputStream, Type.DICTIONARY);
    }

    private Dictionary(String dictionaryFileName)
            throws IOException, FileNotValidException {

        this.dictionaryFileName = dictionaryFileName;
        this.fileInputStream = fileUtils.loadFile(dictionaryFileName);
        this.reader = new Scanner(fileInputStream);

        validateDictionaryFile(dictionaryFileName);
    }

}