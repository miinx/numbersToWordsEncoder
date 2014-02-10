package org.karen.numtowords.io.input;

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
            throws FileNotFoundException {
        FileInputStream fileInputStream = createInputStreamFromFile(fileName);
        this.reader = new Scanner(fileInputStream);
    }

    private FileInputStream createInputStreamFromFile(String fileName)
            throws FileNotFoundException {
        currentFile = fileName;
        return new FileInputStream(fileName);
        // todo: handle exception
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
