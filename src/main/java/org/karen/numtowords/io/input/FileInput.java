package org.karen.numtowords.io.input;

import org.karen.numtowords.exception.NumbersFileNotFoundException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class FileInput implements Input {

    private List<String> fileNames;
    private Scanner reader;
    private String currentFile;

    public FileInput(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public void setReader(String fileName)
            throws NumbersFileNotFoundException {

        FileInputStream fileInputStream = createInputStreamFromFile(fileName);
        this.reader = new Scanner(fileInputStream);
    }

    private FileInputStream createInputStreamFromFile(String fileName)
            throws NumbersFileNotFoundException {

        try {
            currentFile = fileName;
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new NumbersFileNotFoundException("Numbers data file not found: " + fileName);
        }
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
