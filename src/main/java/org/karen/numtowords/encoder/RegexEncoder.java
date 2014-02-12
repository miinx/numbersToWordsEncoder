package org.karen.numtowords.encoder;

import org.karen.numtowords.dictionary.Dictionary;

import java.util.*;

public class RegexEncoder implements Encoder {

    private Dictionary dictionary;

    public static RegexEncoder load(Dictionary dictionary) {
        return new RegexEncoder(dictionary);
    }

    private RegexEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String numberToEncode) {

        Scanner dictionaryReader = dictionary.getReader();

        Number number = new Number(numberToEncode);
        String regexForNumber = number.getRegex();
        Map<Integer, Character> unencodedDigits = number.getUnencodedDigits();

        List<String> matches = new ArrayList<String>();
        String word;
        String postProcessedWord;

        while (dictionaryReader.hasNextLine()) {
            word = dictionaryReader.nextLine();
            if (word.matches(regexForNumber)) {
                postProcessedWord = reinsertUnencodedDigits(word, unencodedDigits);
                matches.add(postProcessedWord);
            }
        }

        return matches;
    }

    private String reinsertUnencodedDigits(String word, Map<Integer, Character> unencodedNumbers) {
        StringBuilder stringBuilder = new StringBuilder(word);

        Iterator<Map.Entry<Integer, Character>> iterator = unencodedNumbers.entrySet().iterator();
        Map.Entry<Integer, Character> pair;

        while (iterator.hasNext()) {
            pair = iterator.next();
            stringBuilder.insert(pair.getKey(), pair.getValue().toString());
        }

        return stringBuilder.toString().toUpperCase();
    }
}
