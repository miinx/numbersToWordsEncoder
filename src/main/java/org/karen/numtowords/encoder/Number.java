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

    private Map<Integer, String> firstWordRegexes = new HashMap<Integer, String>();
    private Map<Integer, String> secondWordRegexes = new HashMap<Integer, String>();

    public Number(String number) {
        this.number = number;

        this.encodingMap = createEncodingMap();
        this.numberAsCharArray = convertToCharArray();
        this.length = numberAsCharArray.length;

        setRegexPartsAndUnencodedDigits();
        buildRegexesToEncodeUpTo2WordsFromNumber();
    }

    public String getRegexForWordWithLength(int whichWord, int wordLength) {
        if (whichWord == 1) {
            return getRegexForFirstWordWithLength(wordLength);
        } else {
            return getRegexForSecondWordWithLength(wordLength);
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

    private String getRegexForFirstWordWithLength(int length) {
        return firstWordRegexes.containsKey(length) ? firstWordRegexes.get(length) : "";
    }

    private String getRegexForSecondWordWithLength(int length) {
        return secondWordRegexes.containsKey(length) ? secondWordRegexes.get(length) : "";
    }

    private void buildRegexesToEncodeUpTo2WordsFromNumber() {
        int maxWordLength = regexForEncodableDigits.size();

        for (int secondWordLength = 0; secondWordLength < maxWordLength; secondWordLength++) {
            int firstWordLength = maxWordLength - secondWordLength;
            addRegexForSpecifiedWordToRegexesForThatWord(1, firstWordLength);
            addRegexForSpecifiedWordToRegexesForThatWord(2, secondWordLength);
        }
    }

    private void addRegexForSpecifiedWordToRegexesForThatWord(int whichWord, int wordLength) {
        StringBuilder wordBuilder = new StringBuilder(CASE_INSENSITIVE_SEARCH_FLAG);
        if (whichWord == 1) {
            buildRegexForFirstWord(wordBuilder, wordLength);
            firstWordRegexes.put(wordLength, wordBuilder.toString());
        } else {
            buildRegexForSecondWord(wordBuilder, wordLength);
            secondWordRegexes.put(wordLength, wordBuilder.toString());
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
