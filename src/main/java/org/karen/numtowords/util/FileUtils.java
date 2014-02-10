package org.karen.numtowords.util;

import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.DictionaryNotFoundException;
import org.karen.numtowords.exception.NumbersFileNotFoundException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;

import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

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

}
