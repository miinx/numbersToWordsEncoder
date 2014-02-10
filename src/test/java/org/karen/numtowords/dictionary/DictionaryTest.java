package org.karen.numtowords.dictionary;

import org.junit.Test;
import org.karen.numtowords.exception.DictionaryNotFoundException;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DictionaryTest {

    private Dictionary dictionary;
    private TestUtils testUtils = new TestUtils();
    private String testDataDirectory = testUtils.getTestClassesDirectory() + "data/";
    private String validDictionary = testDataDirectory + "valid-dictionary.txt";
    private String invalidDictionaryWithNumbers = testDataDirectory + "invalid-dictionary-numbers.txt";
    private String invalidDictionaryWithMultipleWordsPerLine = testDataDirectory + "invalid-dictionary-multiple-words-per-line.txt";

    @Test
    public void validatesExistingDictionaryHasOneWordPerLine()
            throws IOException, FileNotValidException {

        dictionary = new Dictionary(validDictionary);

        assertNotNull(dictionary);
        assertThat(dictionary.getDictionaryFileName(), is(validDictionary));
        assertThat(dictionary.getReader(), isA(Scanner.class));
    }

    @Test(expected = DictionaryNotFoundException.class)
    public void throwsExceptionForNonExistentDictionaryFile()
            throws IOException, FileNotValidException {

        dictionary = new Dictionary("does-not-exist.txt");
    }

    @Test(expected = FileNotValidException.class)
    public void throwsDictionaryNotValidExceptionForDictionaryWithNumbers()
        throws IOException, FileNotValidException {

        dictionary = new Dictionary(invalidDictionaryWithNumbers);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsDictionaryNotValidExceptionForDictionaryWithMultipleWordsPerLine()
            throws Throwable {

        dictionary = new Dictionary(invalidDictionaryWithMultipleWordsPerLine);
    }

    @Test
    public void buildsValidationExceptionMessageWithInvalidLines()
            throws IOException, FileNotValidException {

        // use valid file to avoid exception & enable access to test method on dictionary object
        dictionary = new Dictionary(validDictionary);

        List<String> invalidLines = new ArrayList<String>();
        invalidLines.add("123");
        invalidLines.add("456");
        String expectedMessage = validDictionary + Dictionary.FILE_CONTAINS_INVALID_LINES_MESSAGE + "\n - 123\n - 456";

        String actualMessage = dictionary.buildDictionaryNotValidErrorMessge(invalidLines);

        assertEquals(expectedMessage, actualMessage);
    }
}
