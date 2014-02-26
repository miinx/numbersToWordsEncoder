package org.karen.numtowords.io.input;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.validation.DataType;
import org.karen.numtowords.validation.FileValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileInput implements Input {

    public static final String VALID_NUMBERS_LINE_REGEX = "^[^a-zA-Z]*$";
    private static final String NEXT_NUMBER_MESSAGE = "\nProcessing the next number...";

    private Scanner reader;
    private FileValidator fileValidator = new FileValidator();
    private List<String> filePaths;
    private int filesLoaded = 0;
    private FileInputStream fileInputStream;
    private String nextNumber;

    public static FileInput loadFiles(List<String> filePaths) throws IOException, FileNotValidException {
        return new FileInput(filePaths);
    }

    private FileInput(List<String> filePaths) throws IOException, FileNotValidException {
        this.filePaths = filePaths;
        setReader(filePaths.get(0));
    }

    @Override
    public Scanner getReader() {
        return reader;
    }

    @Override
    public DataType getType() {
        return DataType.FILE;
    }

    @Override
    public String getNextNumberMessage() {
        return NEXT_NUMBER_MESSAGE;
    }

    @Override
    public String getNextNumber() {
        return nextNumber;
    }

    @Override
    public void setNextNumber() throws IOException, FileNotValidException {
        if (reader.hasNextLine()) {
            nextNumber = reader.nextLine();

        } else if (hasUnprocessedFiles()) {
            setReader(filePaths.get(filesLoaded));
            setNextNumber();

        } else {
            nextNumber = EXIT_VALUE;
        }
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    private void setReader(String filePath) throws IOException, FileNotValidException {
        loadNextFile(filePath);
        validateFile(filePath);
        filesLoaded++;
    }

    private boolean hasUnprocessedFiles() {
        return filePaths.size() > filesLoaded;
    }

    private void loadNextFile(String filePath) throws FileNotFoundException {
        fileInputStream = fileValidator.getFileInputStream(filePath);
        reader = new Scanner(new File(filePath));
    }

    private void validateFile(String filePath) throws IOException, FileNotValidException {
        fileValidator.validate(filePath, fileInputStream, DataType.FILE);
    }

}
