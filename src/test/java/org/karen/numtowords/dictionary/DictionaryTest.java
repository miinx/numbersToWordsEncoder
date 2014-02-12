package org.karen.numtowords.dictionary;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertNotNull;

public class DictionaryTest {

    private Dictionary dictionary;

    private File validDictionary;
    private File invalidDictionary;

    @Before
    public void setup()
            throws IOException {
        validDictionary = TestUtils.createTempFileWithProvidedLines("validDictionary", "cat", "dog", "", "mouse");
    }

    @Test
    public void validatesExistingDictionaryHasOneWordPerLine()
            throws IOException, FileNotValidException {

        dictionary = Dictionary.loadFile(validDictionary);

        assertNotNull(dictionary);
        assertThat(dictionary.getDictionaryFile(), is(validDictionary));
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

        invalidDictionary = TestUtils.createTempFileWithProvidedLines("invalidDictionary", "123");

        dictionary = Dictionary.loadFile(invalidDictionary);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsDictionaryNotValidExceptionForDictionaryWithMultipleWordsPerLine()
            throws IOException, FileNotValidException {

        invalidDictionary = TestUtils.createTempFileWithProvidedLines("invalidDictionary", "cat", "dog on a log");

        dictionary = Dictionary.loadFile(invalidDictionary);
    }

}
