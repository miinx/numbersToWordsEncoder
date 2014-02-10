package org.karen.numtowords.dictionary;

import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.util.FileUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary implements Input {

    public static final String MACOSX_SYSTEM_DICTIONARY_PATH = "/usr/share/dict/words";
    public static final String FILE_CONTAINS_INVALID_LINES_MESSAGE = " contains the following invalid lines: ";

    public static final String DICTIONARY_NOT_FOUND_MESSAGE = "Dictionary file not found: ";
    private static final String VALID_DICTIONARY_LINE_REGEX = "^[^\\d ]*$";
    private static final String UNEXPECTED_VALIDATION_EXCEPTION = "An unexpected error occurred while validating the dictionary - ";

    private Scanner reader;
    private FileInputStream fileInputStream;
    private FileUtils fileUtils = new FileUtils();
    private String dictionaryFileName;

    public Dictionary(String dictionaryFileName)
            throws IOException, FileNotValidException {

        this.dictionaryFileName = dictionaryFileName;
        this.fileInputStream = fileUtils.loadFile(dictionaryFileName, Type.DICTIONARY);
        this.reader = new Scanner(fileInputStream);

        validateDictionary();
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

    private void validateDictionary()
            throws IOException, FileNotValidException {

        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        List<String> invalidLines = new ArrayList<String>();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                if (!line.matches(VALID_DICTIONARY_LINE_REGEX)) {
                    invalidLines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IOException(UNEXPECTED_VALIDATION_EXCEPTION + e.getMessage());
        } finally {
            br.close();
        }

        throwNotValidExceptionIfAnyInvalidLines(invalidLines);
    }

    private void throwNotValidExceptionIfAnyInvalidLines(List<String> invalidLines) throws FileNotValidException {
        if (invalidLines.size() > 0) {
            String error = buildDictionaryNotValidErrorMessge(invalidLines);
            throw new FileNotValidException(error);
        }
    }

    protected String buildDictionaryNotValidErrorMessge(List<String> invalidLines) {
        String error = dictionaryFileName + FILE_CONTAINS_INVALID_LINES_MESSAGE;
        for (String invalidLine : invalidLines) {
            error += "\n - " + invalidLine;
        }
        return error;
    }

}