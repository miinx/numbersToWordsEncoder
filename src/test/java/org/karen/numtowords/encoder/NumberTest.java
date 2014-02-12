package org.karen.numtowords.encoder;

import org.junit.Test;
import org.karen.numtowords.exception.FileNotValidException;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

public class NumberTest {

    private Number number;

    @Test
    public void createsNumbersToLettersMap()
            throws IOException, FileNotValidException {

        number = new Number("1");

        Map<Character, String> numbersToLetters = number.getEncodingMap();

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



}
