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
    public void encodesValidNumber()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("questionably", "notamatch");

        List<String> matches = encoder.encode("783784662259");

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0), is("QUESTIONABLY"));
    }

    @Test
    public void returnsAllMatchesInUppercase()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("then", "there", "them", "thence");

        List<String> matches = encoder.encode("8436");

        assertThat(matches.size(), is(2));
        assertThat(matches.get(0), is("THEN"));
        assertThat(matches.get(1), is("THEM"));
    }

    @Test
    public void removesPunctuationAndWhitespaceFromNumber()
            throws IOException, FileNotValidException {

        testDictionary = TestUtils.createTempFileWithProvidedLines("testDictionary", "barn", "barren");
        dictionary = Dictionary.loadFile(testDictionary);
        encoder = RegexEncoder.load(dictionary);

        List<String> matches = encoder.encode("2 27-*6");

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0), is("BARN"));
    }

    @Test
    public void leavesdigits1And0InPlace()
            throws IOException, FileNotValidException {

        encoder = createRegexEncoderWithDictionaryContainingLines("fan", "dam");
                                                                    // [DEF][ABC][MNO]
        List<String> matches = encoder.encode("31206");

        assertThat(matches.size(), is(2));
        assertThat(matches.get(0), is("F1A0N"));
        assertThat(matches.get(1), is("D1A0M"));
    }


    private RegexEncoder createRegexEncoderWithDictionaryContainingLines(String... lines)
            throws IOException, FileNotValidException {

        testDictionary = TestUtils.createTempFileWithProvidedLines("testDictionary", lines);
        dictionary = Dictionary.loadFile(testDictionary);
        return RegexEncoder.load(dictionary);
    }

}
