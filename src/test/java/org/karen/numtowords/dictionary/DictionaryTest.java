package org.karen.numtowords.dictionary;

import org.junit.Test;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertNotNull;

public class DictionaryTest {

    private Dictionary dictionary;
    private TestUtils testUtils = new TestUtils();
    private String testDataDirectory = testUtils.getTestClassesDirectory() + "data/";
    private String validDictionary = testDataDirectory + "valid-dictionary.txt";
    private String invalidDictionaryWithNumbers = testDataDirectory + "invalid-dictionary--numbers.txt";
    private String invalidDictionaryWithMultipleWordsPerLine = testDataDirectory + "invalid-dictionary--multiple-words-per-line.txt";

    @Test
    public void validatesExistingDictionaryHasOneWordPerLine()
            throws IOException, FileNotValidException {

        dictionary = Dictionary.load(validDictionary);

        assertNotNull(dictionary);
        assertThat(dictionary.getDictionaryFileName(), is(validDictionary));
        assertThat(dictionary.getReader(), isA(Scanner.class));
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsExceptionForNonExistentDictionaryFile()
            throws IOException, FileNotValidException {

        dictionary = Dictionary.load("does-not-exist.txt");
    }

    @Test(expected = FileNotValidException.class)
    public void throwsDictionaryNotValidExceptionForDictionaryWithNumbers()
        throws IOException, FileNotValidException {

        dictionary = Dictionary.load(invalidDictionaryWithNumbers);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsDictionaryNotValidExceptionForDictionaryWithMultipleWordsPerLine()
            throws IOException, FileNotValidException {

        dictionary = Dictionary.load(invalidDictionaryWithMultipleWordsPerLine);
    }

}
