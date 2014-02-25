package org.karen.numtowords.dictionary;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.validation.FileValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String VALID_DICTIONARY_LINE_REGEX = "^[^\\d ]*$";

    private Scanner reader;
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
    public Scanner getReader() {
        return reader;
    }

    @Override
    public Type getType() {
        return Type.DICTIONARY;
    }

    @Override
    public String getNextNumber() {
        return null;        // todo hmm... change interface? or dictionary? ... review
    }

    public File getDictionaryFile() {
        return dictionaryFile;
    }

    private Dictionary(String dictionaryFileName)
            throws IOException, FileNotValidException {

        this.dictionaryFile = new File(dictionaryFileName);
        this.dictionaryInputStream = new FileInputStream(dictionaryFile);
        this.reader = new Scanner(dictionaryFile);

        validate(dictionaryFileName);
    }

    private Dictionary(File dictionaryFile)
            throws IOException, FileNotValidException {

        this.dictionaryFile = dictionaryFile;
        this.dictionaryInputStream = new FileInputStream(dictionaryFile);
        this.reader = new Scanner(dictionaryFile);

        validate(dictionaryFile.getName());
    }

    private void validate(String dictionaryFileName)
            throws IOException, FileNotValidException {

        fileValidator.validate(dictionaryFileName, dictionaryInputStream, Type.DICTIONARY);
    }

}