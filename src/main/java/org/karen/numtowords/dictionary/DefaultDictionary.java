package org.karen.numtowords.dictionary;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.validation.DataType;
import org.karen.numtowords.validation.FileValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DefaultDictionary implements Dictionary {

    public static final String SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String VALID_DICTIONARY_LINE_REGEX = "^[^\\d ]*$";

    private File dictionaryFile;
    private FileInputStream dictionaryInputStream;

    private FileValidator fileValidator = new FileValidator();

    public static DefaultDictionary load(String dictionaryFilePath) throws IOException, FileNotValidException {
        return new DefaultDictionary(dictionaryFilePath);
    }

    private DefaultDictionary(String dictionaryFilePath) throws IOException, FileNotValidException {

        this.dictionaryFile = new File(dictionaryFilePath);
        this.dictionaryInputStream = new FileInputStream(dictionaryFile);

        validate(dictionaryFile.getName());
    }

    @Override
    public Scanner getReader() throws FileNotFoundException {
        return new Scanner(dictionaryFile);
    }

    @Override
    public DataType getType() {
        return DataType.DICTIONARY;
    }

    @Override
    public File getDictionaryFile() {
        return dictionaryFile;
    }

    private void validate(String dictionaryFileName) throws IOException, FileNotValidException {

        fileValidator.validate(dictionaryFileName, dictionaryInputStream, DataType.DICTIONARY);
    }


}