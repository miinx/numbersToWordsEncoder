package org.karen.numtowords.util;

import org.junit.Test;
import org.karen.numtowords.exception.DictionaryNotFoundException;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.exception.NumbersFileNotFoundException;
import org.karen.numtowords.io.input.Input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileUtilsTest {

    private FileUtils fileUtils = new FileUtils();
    private TestUtils testUtils = new TestUtils();

    private String testDataDir = testUtils.getTestClassesDirectory() + "data/";
    private String validDictionary = testDataDir + "valid-dictionary.txt";
    private String validNumbersData = testDataDir + "valid-numbers.txt";
    private String invalidDictionaryWithNumbers = testDataDir + "invalid-dictionary--numbers.txt";
    private String invalidDictionaryWithMultipleWordsPerLine = testDataDir + "invalid-dictionary--multiple-words-per-line.txt";;
    private String invalidNumbersDataWithWords = testDataDir + "invalid-numbers--words.txt";

    @Test
    public void createsFileInputStreamForExistingDictionaryFile()
            throws IOException {

        FileInputStream fileInputStream = fileUtils.loadFile(validDictionary, Input.Type.DICTIONARY);
        assertNotNull(fileInputStream);
    }

    @Test
    public void createsFileInputStreamForExistingNumbersDataFile()
            throws IOException {

        FileInputStream fileInputStream = fileUtils.loadFile(validNumbersData, Input.Type.FILE);
        assertNotNull(fileInputStream);
    }

    @Test(expected = DictionaryNotFoundException.class)
    public void throwsExceptionWhenDictionaryFileIsNotFound()
            throws IOException {

        fileUtils.loadFile("does-not-exist.txt", Input.Type.DICTIONARY);
    }

    @Test(expected = NumbersFileNotFoundException.class)
    public void throwsExceptionWhenNumbersDataFileIsNotFound()
            throws IOException {

        fileUtils.loadFile("does-not-exist.txt", Input.Type.FILE);
    }

    @Test
    public void validatesValidDictionaryFile()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileUtils.loadFile(validDictionary, Input.Type.DICTIONARY);
        fileUtils.validate(validDictionary, fileInputStream, Input.Type.DICTIONARY);

        // todo: nothing to assert? error thrown if invalid, no error thus test passes!
    }

    @Test
    public void validatesValidNumbersDataFile()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileUtils.loadFile(validNumbersData, Input.Type.FILE);
        fileUtils.validate(validNumbersData, fileInputStream, Input.Type.FILE);

        // todo: as above, nothing can be asserted?
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidDictionaryContainingNumbers()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileUtils.loadFile(invalidDictionaryWithNumbers, Input.Type.DICTIONARY);
        fileUtils.validate(invalidDictionaryWithNumbers, fileInputStream, Input.Type.DICTIONARY);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidDictionaryContainingMultipleWordsPerLine()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileUtils.loadFile(invalidDictionaryWithMultipleWordsPerLine, Input.Type.DICTIONARY);
        fileUtils.validate(invalidDictionaryWithMultipleWordsPerLine, fileInputStream, Input.Type.DICTIONARY);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidNumbersData()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileUtils.loadFile(invalidNumbersDataWithWords, Input.Type.FILE);
        fileUtils.validate(invalidNumbersDataWithWords, fileInputStream, Input.Type.FILE);
    }

    @Test
    public void buildsValidationExceptionMessageWithInvalidLines()
            throws IOException, FileNotValidException {

        List<String> invalidLines = new ArrayList<String>();
        invalidLines.add("123");
        invalidLines.add("456");

        String fileName = "file.txt";

        FileUtils fileUtils = new FileUtils();

        String expectedMessage = fileName + FileUtils.FILE_CONTAINS_INVALID_LINES_MESSAGE + "\n - 123\n - 456";
        String actualMessage = fileUtils.buildFileNotValidExceptionMessage(fileName, invalidLines);

        assertEquals(expectedMessage, actualMessage);
    }

}
