package org.karen.numtowords.dictionary;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.validation.FileValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String VALID_DICTIONARY_LINE_REGEX = "^[^\\d ]*$";

    private File dictionaryFile;
    private FileInputStream dictionaryInputStream;

    private FileValidator fileValidator = new FileValidator();

    public static Dictionary load(String fileName)
            throws IOException, FileNotValidException {

        return new Dictionary(fileName);
    }

    public static Dictionary loadFile(File file)
            throws IOException, FileNotValidException {

        return new Dictionary(file);
    }

    @Override
    public Scanner getReader() throws FileNotFoundException {
        return new Scanner(dictionaryFile);
    }

    @Override
    public Type getType() {
        return Type.DICTIONARY;     // todo FileType??
    }

    public File getDictionaryFile() {
        return dictionaryFile;
    }

    private Dictionary(String dictionaryFileName)
            throws IOException, FileNotValidException {

        this.dictionaryFile = new File(dictionaryFileName);
        this.dictionaryInputStream = new FileInputStream(dictionaryFile);

        validate(dictionaryFileName);
    }

    private Dictionary(File dictionaryFile)
            throws IOException, FileNotValidException {

        this.dictionaryFile = dictionaryFile;
        this.dictionaryInputStream = new FileInputStream(dictionaryFile);

        validate(dictionaryFile.getName());
    }

    private void validate(String dictionaryFileName)
            throws IOException, FileNotValidException {

        fileValidator.validate(dictionaryFileName, dictionaryInputStream, Type.DICTIONARY);
    }


    // --------- ALL AFTER THIS SHOULD GO ---------
    // todo new interface for dictionary, remove all the stuff from here that doesn't use it

    @Override
    public String getNextNumberMessage() {
        return null;
    }

    @Override
    public String getNextNumber() {
        return null;
    }

    @Override
    public void setNextNumber() {

    }


}