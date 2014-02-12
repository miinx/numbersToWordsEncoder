package org.karen.numtowords.app;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RegexEncoderTest {

    private TestUtils testUtils = new TestUtils();
    private String testDataDir = testUtils.getTestClassesDirectory() + "data/";

    private RegexEncoder encoder;
    private Dictionary  dictionary;

    @Before
    public void setup()
            throws IOException, FileNotValidException {

        dictionary = Dictionary.load(testDataDir + "valid-dictionary.txt");
        encoder = RegexEncoder.load(dictionary);
    }

    @Test
    public void createsNumbersToLettersMap() {
        Map<Character, String> numbersToLetters = encoder.getEncodingMap();

        assertNotNull(numbersToLetters);
        assertThat(numbersToLetters.get('1'), is(""));
        assertThat(numbersToLetters.get('2'), is("[ABC]"));
        assertThat(numbersToLetters.get('3'), is("[DEF]"));
        assertThat(numbersToLetters.get('4'), is("[GHI]"));
        assertThat(numbersToLetters.get('5'), is("[JKL]"));
        assertThat(numbersToLetters.get('6'), is("[MNO]"));
        assertThat(numbersToLetters.get('7'), is("[PQRS]"));
        assertThat(numbersToLetters.get('8'), is("[TUV]"));
        assertThat(numbersToLetters.get('9'), is("[WXYZ]"));
        assertThat(numbersToLetters.get('0'), is(""));
    }

    @Test
    public void generatesRegexForValidNumber() {
        String validNumber = "23456789";
        String regex = encoder.getRegexForNumber(validNumber);
        assertEquals("[ABC][DEF][GHI][JKL][MNO][PQRS][TUV][WXYZ]", regex);
    }


}
