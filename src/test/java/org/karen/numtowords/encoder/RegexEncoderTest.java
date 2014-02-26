package org.karen.numtowords.encoder;

import org.junit.Test;
import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RegexEncoderTest {

    private RegexEncoder encoder;
    private Dictionary  dictionary;
    private File testDictionary;

    @Test
    public void encodesSimpleValidNumber()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("cat");

        List<String> matches = encoder.encode("228");

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0), is("CAT"));
    }

    @Test
    public void encodesValidNumberWithAllEncodableDigitsUsed()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("questionably", "notamatch");

        List<String> matches = encoder.encode("783784662259");

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0), is("QUESTIONABLY"));
    }

    @Test
    public void returnsZeroResultsForInvalidNumber()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("cat");

        List<String> matches = encoder.encode("cat");

        assertThat(matches.size(), is(0));
    }

    @Test
    public void returnsResultInUppercaseAndSorted()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("then", "there", "them", "thence");

        List<String> matches = encoder.encode("8436");

        assertThat(matches.size(), is(2));
        assertThat(matches.get(0), is("THEM"));
        assertThat(matches.get(1), is("THEN"));
    }

    @Test
    public void removesPunctuationAndWhitespaceFromNumber()
            throws IOException, FileNotValidException {

        testDictionary = TestUtils.createTempFileWithProvidedLines("testDictionary", "barn", "barren");
        dictionary = Dictionary.loadFile(testDictionary);
        encoder = RegexEncoder.load(dictionary);

        List<String> matches = encoder.encode(" 227- 6 ");

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0), is("BARN"));
    }

    @Test
    public void leavesDigits1And0InPlace()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("fan", "dam", "it");

        List<String> matches = encoder.encode("3126408");

        assertThat(matches.size(), is(2));
        assertThat(matches.get(0), is("D1AM-I0T"));
        assertThat(matches.get(1), is("F1AN-I0T"));
    }

    @Test
    public void onlyMatchesWordsEqualInLengthToSourceNumber()   // currently!
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("and", "dam", "dame", "dames", "fame", "famed", "famous");

        List<String> matches = encoder.encode("3263");

        assertThat(matches.size(), is(2));
        assertThat(matches.get(0), is("DAME"));
        assertThat(matches.get(1), is("FAME"));
    }

    @Test
    public void combinesUpTo2WordsForMatchIfWordLengthIsShorterThanNumberLength()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("call", "bb", "me", "joe", "klod", "cal");

        List<String> matches = encoder.encode("225563");

        assertThat(matches.size(), is(3));
        assertThat(matches.get(0), is("BB-KLOD"));
        assertThat(matches.get(1), is("CAL-JOE"));
        assertThat(matches.get(2), is("CALL-ME"));
    }

    @Test
    public void doesNotReturnMatchesForSecondWordInMultipleWordMatchIfFirstWordHasNone()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("me");

        List<String> matches = encoder.encode("225563");

        assertThat(matches.size(), is(0));
    }

    private RegexEncoder createRegexEncoderWithDictionaryContainingLines(String... lines)
            throws IOException, FileNotValidException {

        testDictionary = TestUtils.createTempFileWithProvidedLines("testDictionary", lines);
        dictionary = Dictionary.loadFile(testDictionary);
        return RegexEncoder.load(dictionary);
    }

}
