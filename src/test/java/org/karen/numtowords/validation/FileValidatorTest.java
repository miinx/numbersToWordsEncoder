package org.karen.numtowords.validation;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.util.TestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileValidatorTest {

    private FileValidator fileValidator = new FileValidator();

    private String validDictionary;
    private String validNumbersData;
    private String invalidDictionaryWithNumbers;
    private String invalidDictionaryWithMultipleWordsPerLine;
    private String invalidNumbersDataWithWords;

    @Before
    public void setup()
            throws IOException {

        validDictionary = TestUtils.createTempFileWithProvidedLines("dictionary", "apple").getPath();
        validNumbersData = TestUtils.createTempFileWithProvidedLines("numbers", "123").getPath();
        invalidDictionaryWithNumbers = TestUtils.createTempFileWithProvidedLines("dictionary", "123").getPath();
        invalidDictionaryWithMultipleWordsPerLine = TestUtils.createTempFileWithProvidedLines("dictionary", "cat in the hat").getPath();
        invalidNumbersDataWithWords = TestUtils.createTempFileWithProvidedLines("numbers", "oops").getPath();
    }

    @Test
    public void createsFileInputStreamForExistingFile()
            throws IOException {

        FileInputStream fileInputStream = fileValidator.getFileInputStream(validDictionary);

        assertNotNull(fileInputStream);
        assertThat(fileInputStream, isA(FileInputStream.class));
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsExceptionWhenFileIsNotFound()
            throws IOException {

        fileValidator.getFileInputStream("does-not-exist.txt");
    }

    @Test
    public void validatesValidDictionaryFile()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileValidator.getFileInputStream(validDictionary);
        fileValidator.validate(validDictionary, fileInputStream, Input.Type.DICTIONARY);

        // todo: nothing to assert? error thrown if invalid, no error thus test passes!
    }

    @Test
    public void validatesValidNumbersDataFile()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileValidator.getFileInputStream(validNumbersData);
        fileValidator.validate(validNumbersData, fileInputStream, Input.Type.FILE);

        // todo: as above, nothing can be asserted?
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidDictionaryContainingNumbers()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileValidator.getFileInputStream(invalidDictionaryWithNumbers);
        fileValidator.validate(invalidDictionaryWithNumbers, fileInputStream, Input.Type.DICTIONARY);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidDictionaryContainingMultipleWordsPerLine()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileValidator.getFileInputStream(invalidDictionaryWithMultipleWordsPerLine);
        fileValidator.validate(invalidDictionaryWithMultipleWordsPerLine, fileInputStream, Input.Type.DICTIONARY);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidNumbersData()
            throws IOException, FileNotValidException {

        FileInputStream fileInputStream = fileValidator.getFileInputStream(invalidNumbersDataWithWords);
        fileValidator.validate(invalidNumbersDataWithWords, fileInputStream, Input.Type.FILE);
    }

    @Test
    public void buildsValidationExceptionMessageWithInvalidLines()
            throws IOException, FileNotValidException {

        List<String> invalidLines = new ArrayList<String>();
        invalidLines.add("123");
        invalidLines.add("456");

        String fileName = "file.txt";

        FileValidator fileValidator = new FileValidator();

        String expectedMessage = fileName + FileValidator.FILE_CONTAINS_INVALID_LINES_MESSAGE + "\n - 123\n - 456";
        String actualMessage = fileValidator.buildFileNotValidExceptionMessage(fileName, invalidLines);

        assertEquals(expectedMessage, actualMessage);
    }

}
