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
    private List<String> regexCharacterClasses = new ArrayList<String>();
    private Map<Integer, Character> unencodedDigits = new HashMap<Integer, Character>();

    private Map<Integer, String> word1Regexes = new HashMap<Integer, String>();
    private Map<Integer, String> word2Regexes = new HashMap<Integer, String>();

    public Number(String number) {
        this.number = number;

        this.encodingMap = createEncodingMap();
        this.numberAsCharArray = convertToCharArray();
        this.length = numberAsCharArray.length;

        setRegexPartsAndUnencodedDigits();
        buildRegexesForUpTo2Words();
    }

    public String getRegexForRequestedWordAndWordLength(int whichWord, int wordLength) {
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
            String charEncoding = encodingMap.get(numberAsCharArray[i]);

            if (charEncoding.equals("")) {
                unencodedDigits.put(i, numberAsCharArray[i]);
            } else {
                regexCharacterClasses.add(charEncoding);
            }
        }
    }

    private String getRegexForWord1WithLength(int length) {
        return word1Regexes.containsKey(length) ? word1Regexes.get(length) : "";
    }

    private String getRegexForWord2WithLength(int length) {
        return word2Regexes.containsKey(length) ? word2Regexes.get(length) : "";
    }

    private void buildRegexesForUpTo2Words() {

        StringBuilder word1Builder;
        StringBuilder word2Builder;
        int word1Length;
        int maxWordLength = regexCharacterClasses.size();

        for (int word2Length = 0; word2Length < maxWordLength; word2Length++) {

            word1Builder = new StringBuilder(CASE_INSENSITIVE_SEARCH_FLAG);
            word2Builder = new StringBuilder(CASE_INSENSITIVE_SEARCH_FLAG);

            word1Length = maxWordLength - word2Length;

            buildWord1Regex(word1Builder, word1Length);
            buildWord2Regex(word2Builder, word2Length);

            word1Regexes.put(word1Length, word1Builder.toString());
            word2Regexes.put(word2Length, word2Builder.toString());
        }

    }

    private void buildWord1Regex(StringBuilder word1Builder, int size) {
        for (int i = 0; i < size; i++) {
            word1Builder.append(regexCharacterClasses.get(i));
        }
    }

    private void buildWord2Regex(StringBuilder word2Builder, int word2Length) {
        for (int i = regexCharacterClasses.size() - word2Length; i < regexCharacterClasses.size(); i++) {
            word2Builder.append(regexCharacterClasses.get(i));
        }
    }

}
