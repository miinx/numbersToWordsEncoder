package org.karen.numtowords.app;

import org.karen.numtowords.dictionary.Dictionary;

import java.util.HashMap;
import java.util.Map;

public class RegexEncoder implements Encoder {

    private Dictionary dictionary;
    private Map<Character, String> encodingMap;

    public static RegexEncoder load(Dictionary dictionary) {
        return new RegexEncoder(dictionary);
    }

    Map<Character, String> getEncodingMap() {
        return encodingMap;
    }

    public String getRegexForNumber(String number) {
        char[] charsInNumber = number.toCharArray();
        String regex = "";
        for (int i = 0; i < charsInNumber.length; i++) {
            regex += encodingMap.get(charsInNumber[i]);
        }
        return regex;
    }

    private RegexEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.encodingMap = createEncodingMap();
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

}
