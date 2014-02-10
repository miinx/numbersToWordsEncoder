package org.karen.numtowords.io.input;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileInput implements Input {

    public static final String NUMBERS_FILE_NOT_FOUND_MESSAGE = "Numbers data file not found: ";
    public static final String VALID_NUMBERS_LINE_REGEX = "^[^a-zA-Z]*$";

    private Scanner reader;
    private FileUtils fileUtils = new FileUtils();
    private List<String> fileNames;
    private String currentFile;
    private FileInputStream fileInputStream;

    public FileInput(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public void setReader(String fileName)
            throws IOException, FileNotValidException {

        loadFile(fileName);
        validateFile(fileName);
        setCurrentFile(fileName);
    }

    @Override
    public Scanner getReader() {
        return reader;
    }

    @Override
    public Type getType() {
        return Type.FILE;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    private void loadFile(String fileName)
            throws IOException {

        fileInputStream = fileUtils.loadFile(fileName, Type.FILE);
        this.reader = new Scanner(fileInputStream);
    }

    private void validateFile(String fileName)
            throws IOException, FileNotValidException {

        fileUtils.validate(fileName, fileInputStream, Type.FILE);
    }

}
