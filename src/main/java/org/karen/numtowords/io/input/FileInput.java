package org.karen.numtowords.io.input;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.validation.FileValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileInput implements Input {

    public static final String VALID_NUMBERS_LINE_REGEX = "^[^a-zA-Z]*$";
    private static final String NEXT_NUMBER_MESSAGE = "Processing the next number...";

    private Scanner reader;
    private FileValidator fileValidator = new FileValidator();
    private List<String> filePaths;
    private String currentFile;
    private FileInputStream fileInputStream;

    public static FileInput loadFiles(List<String> filePaths) {
        return new FileInput(filePaths);
    }

    public void setReader(String filePath)
            throws IOException, FileNotValidException {

        loadFile(filePath);
        validateFile(filePath);
        setCurrentFile(filePath);
    }

    @Override
    public Scanner getReader() {
        return reader;
    }

    @Override
    public Type getType() {
        return Type.FILE;
    }

    @Override
    public String getNextNumberMessage() {
        return NEXT_NUMBER_MESSAGE;
    }

    @Override
    public String getNextNumber() {
        return null;
    }

    @Override
    public void setNextNumber() {

    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    private FileInput(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    private void loadFile(String filePath)
            throws IOException {

        fileInputStream = fileValidator.getFileInputStream(filePath);
        this.reader = new Scanner(fileInputStream);
    }

    private void validateFile(String filePath)
            throws IOException, FileNotValidException {

        fileValidator.validate(filePath, fileInputStream, Type.FILE);
    }

}
