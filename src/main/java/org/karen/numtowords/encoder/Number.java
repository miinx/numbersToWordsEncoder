package org.karen.numtowords.encoder;

import java.util.HashMap;
import java.util.Map;

public class Number {

    private static final String CASE_INSENSITIVE_SEARCH_FLAG = "(?i)";

    private String number;
    private char[] numberAsCharArray;
    private String regex = "";
    private Map<Integer, Character> unencodedDigits = new HashMap<Integer, Character>();

    private Map<Character,String> encodingMap;

    public Number(String number) {
        this.number = number;

        this.encodingMap = createEncodingMap();
        this.numberAsCharArray = convertToCharArray();
        this.regex = setRegexAndUncodedDigitsForNumber();
    }

    public String getRegex() {
        return regex;
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

    private String setRegexAndUncodedDigitsForNumber() {

        for (int i = 0; i < numberAsCharArray.length; i++) {

            if (encodingMap.get(numberAsCharArray[i]).equals("")) {
                unencodedDigits.put(i, numberAsCharArray[i]);

            } else {
                regex += encodingMap.get(numberAsCharArray[i]);
            }
        }
        return CASE_INSENSITIVE_SEARCH_FLAG + regex;
    }
}
