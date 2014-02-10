package org.karen.numtowords.io.input;

import org.karen.numtowords.util.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileInput implements Input {

    public static final String NUMBERS_FILE_NOT_FOUND_MESSAGE = "Numbers data file not found: ";

    private Scanner reader;
    private FileUtils fileUtils = new FileUtils();
    private List<String> fileNames;
    private String currentFile;

    public FileInput(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public void setReader(String fileName)
            throws IOException {

        this.reader = fileUtils.loadFile(fileName, Type.FILE);
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
}
