package org.karen.numtowords.encoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Number {

    private static final String CASE_INSENSITIVE_SEARCH_FLAG = "(?i)";

    private String number;
    private char[] numberAsCharArray;
    private int length;

    private Map<Character,String> encodingMap;
    private List<String> regexForEncodableDigits = new ArrayList<String>();
    private Map<Integer, Character> unencodedDigits = new HashMap<Integer, Character>();

    private Map<Integer, String> word1Regexes = new HashMap<Integer, String>();
    private Map<Integer, String> word2Regexes = new HashMap<Integer, String>();

    public Number(String number) {
        this.number = number;

        this.encodingMap = createEncodingMap();
        this.numberAsCharArray = convertToCharArray();
        this.length = numberAsCharArray.length;

        setRegexPartsAndUnencodedDigits();
        buildRegexesToEncodeUpTo2WordsFromNumber();
    }

    public String getRegexForRequestedWordWithWordLength(int whichWord, int wordLength) {
        if (whichWord == 1) {
            return getRegexForWord1WithLength(wordLength);
        } else {
            return getRegexForWord2WithLength(wordLength);
        }
    }

    public int getLength() {
        return length;
    }

    public Map<Integer, Character> getUnencodedDigits() {
        return unencodedDigits;
    }

    public Map<Character, String> getEncodingMap() {
        return encodingMap;
    }

    private Map<Character, String> createEncodingMap() {
        Map<Character, String> map = new HashMap<Character, String>();
        map.put('1', "");
        map.put('2', "[ABC]");
        map.put('3', "[DEF]");
        map.put('4', "[GHI]");
        map.put('5', "[JKL]");
        map.put('6', "[MNO]");
        map.put('7', "[PQRS]");
        map.put('8', "[TUV]");
        map.put('9', "[WXYZ]");
        map.put('0', "");
        return map;
    }

    private char[] convertToCharArray() {
        String cleanedNumber = number.replaceAll("[^\\d]", "");
        return cleanedNumber.toCharArray();
    }

    private void setRegexPartsAndUnencodedDigits() {
        for (int i = 0; i < numberAsCharArray.length; i++) {
            String digitEncoding = encodingMap.get(numberAsCharArray[i]);

            if (digitEncoding.equals("")) {
                unencodedDigits.put(i, numberAsCharArray[i]);
            } else {
                regexForEncodableDigits.add(digitEncoding);
            }
        }
    }

    private String getRegexForWord1WithLength(int length) {
        return word1Regexes.containsKey(length) ? word1Regexes.get(length) : "";
    }

    private String getRegexForWord2WithLength(int length) {
        return word2Regexes.containsKey(length) ? word2Regexes.get(length) : "";
    }

    private void buildRegexesToEncodeUpTo2WordsFromNumber() {
        int maxWordLength = regexForEncodableDigits.size();

        for (int word2Length = 0; word2Length < maxWordLength; word2Length++) {
            int word1Length = maxWordLength - word2Length;
            addRegexForSpecifiedWordToRegexesForThatWord(1, word1Length);
            addRegexForSpecifiedWordToRegexesForThatWord(2, word2Length);
        }
    }

    private void addRegexForSpecifiedWordToRegexesForThatWord(int whichWord, int wordLength) {
        StringBuilder wordBuilder = new StringBuilder(CASE_INSENSITIVE_SEARCH_FLAG);
        if (whichWord == 1) {
            buildRegexForFirstWord(wordBuilder, wordLength);
            word1Regexes.put(wordLength, wordBuilder.toString());
        } else {
            buildRegexForSecondWord(wordBuilder, wordLength);
            word2Regexes.put(wordLength, wordBuilder.toString());
        }
    }

    private void buildRegexForFirstWord(StringBuilder wordBuilder, int length) {
        for (int i = 0; i < length; i++) {
            wordBuilder.append(regexForEncodableDigits.get(i));
        }
    }

    private void buildRegexForSecondWord(StringBuilder wordBuilder, int length) {
        for (int i = regexForEncodableDigits.size() - length; i < regexForEncodableDigits.size(); i++) {
            wordBuilder.append(regexForEncodableDigits.get(i));
        }
    }

}
