package org.karen.numtowords.dictionary;

import org.junit.Test;
import org.karen.numtowords.exception.DictionaryNotFoundException;
import org.karen.numtowords.util.TestUtils;

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

    @Test
    public void loadsExistingDictionaryFile()
            throws IOException {

        dictionary = new Dictionary(validDictionary);

        assertNotNull(dictionary);
        assertThat(dictionary.getDictionaryFileName(), is(validDictionary));
        assertThat(dictionary.getReader(), isA(Scanner.class));
    }

    @Test(expected = DictionaryNotFoundException.class)
    public void throwsExceptionForNonExistentDictionaryFile()
            throws IOException {

        dictionary = new Dictionary("does-not-exist.txt");
    }

}
