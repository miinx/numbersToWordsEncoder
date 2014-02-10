package org.karen.numtowords.util;

import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.DictionaryNotFoundException;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.exception.NumbersFileNotFoundException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static final String FILE_CONTAINS_INVALID_LINES_MESSAGE = " contains the following invalid lines: ";
    private static final String UNEXPECTED_VALIDATION_EXCEPTION = "An unexpected error occurred while validating the file - ";

    private static String VALID_LINE_REGEX;

    public FileUtils() {
    }

    public FileInputStream loadFile(String fileName, Input.Type inputType)
            throws IOException {

        try {
            return new FileInputStream(fileName);

        } catch (java.io.FileNotFoundException e) {

            if (Input.Type.FILE.equals(inputType)) {
                throw new NumbersFileNotFoundException(FileInput.NUMBERS_FILE_NOT_FOUND_MESSAGE + fileName, e);
            } else {
                throw new DictionaryNotFoundException(Dictionary.DICTIONARY_NOT_FOUND_MESSAGE + fileName, e);
            }
        }
    }

    public void validate(String fileName, FileInputStream fileInputStream, Input.Type inputType)
            throws IOException, FileNotValidException {

        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        List<String> invalidLines = new ArrayList<String>();
        String line;

        VALID_LINE_REGEX = setValidRegex(inputType);

        try {
            while ((line = br.readLine()) != null) {
                if (!line.matches(VALID_LINE_REGEX)) {
                    invalidLines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IOException(UNEXPECTED_VALIDATION_EXCEPTION + fileName + ", exception was " + e.getMessage());

        } finally {
            closeBufferedReader(br);
        }

        throwNotValidExceptionIfAnyInvalidLines(fileName, invalidLines);

    }

    private String setValidRegex(Input.Type inputType) {
        return Input.Type.FILE.equals(inputType) ?
                FileInput.VALID_NUMBERS_LINE_REGEX : Dictionary.VALID_DICTIONARY_LINE_REGEX;
    }

    private void throwNotValidExceptionIfAnyInvalidLines(String fileName, List<String> invalidLines)
            throws FileNotValidException {

        if (invalidLines.size() > 0) {
            String error = buildFileNotValidExceptionMessage(fileName, invalidLines);
            throw new FileNotValidException(error);
        }
    }

    protected String buildFileNotValidExceptionMessage(String fileName, List<String> invalidLines)
            throws FileNotValidException {

        String error = fileName + FILE_CONTAINS_INVALID_LINES_MESSAGE;
        for (String invalidLine : invalidLines) {
            error += "\n - " + invalidLine;
        }
        return error;
    }

    private void closeBufferedReader(BufferedReader br) {
        try {
            br.close();
        } catch (IOException e) {
            // reader is closed
        }
    }

}
