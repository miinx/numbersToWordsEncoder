package org.karen.numtowords.encoder;

import org.karen.numtowords.dictionary.Dictionary;

import java.util.*;

public class RegexEncoder implements Encoder {

    private Dictionary dictionary;
    private Number number;
    private List<String> allMatches = new ArrayList<String>();

    public static RegexEncoder load(Dictionary dictionary) {
        return new RegexEncoder(dictionary);
    }

    private RegexEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String numberToEncode) {

        // currently this can only be null or a value
        if (numberToEncode.equals("") || numberToEncode.equals(null)) {
            return allMatches;
        }

        Scanner dictionaryReader = dictionary.getReader();
        number = new Number(numberToEncode);

        Map<Integer, List<String>> matchesForFirstWordByLength = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> matchesForSecondWordByLength = new HashMap<Integer, List<String>>();

        String word;
        int wordLength;

        while (dictionaryReader.hasNextLine()) {

            word = dictionaryReader.nextLine();
            wordLength = word.length();

            if (wordLength <= number.getLength()) {
                getMatchesForWordLength(wordLength, word, matchesForFirstWordByLength, 1);
                getMatchesForWordLength(wordLength, word, matchesForSecondWordByLength, 2);
            }
        }

        buildListOfAllMatches(matchesForFirstWordByLength, matchesForSecondWordByLength);

        return allMatches;
    }

    private void getMatchesForWordLength(int wordLength, String word,
                                         Map<Integer, List<String>> matchesByWordLength, int whichWord) {

        String regex = number.getRegexForWordWithLength(whichWord, wordLength);

        if (word.matches(regex)) {
            addWordToListOfMatchesByWordLength(matchesByWordLength, wordLength, word);
        }
    }

    private void addWordToListOfMatchesByWordLength(Map<Integer, List<String>> matchesGroup, int wordLength, String word) {
        if (!matchesGroup.containsKey(wordLength)) {
            matchesGroup.put(wordLength, new ArrayList<String>());
        }
        matchesGroup.get(wordLength).add(word);
    }

    private void buildListOfAllMatches(Map<Integer, List<String>> matchesForFirstWord, Map<Integer, List<String>> matchesForSecondWord) {
        int maxWordLength = number.getLength();

        for (int wordLength=1; wordLength<=maxWordLength; wordLength++) {
            if (matchesForFirstWord.containsKey(wordLength)) {
                buildMatchesForCombinedWords(matchesForFirstWord, matchesForSecondWord, maxWordLength, wordLength);
            }
        }
        Collections.sort(allMatches);
    }

    private void buildMatchesForCombinedWords(Map<Integer, List<String>> matchesForFirstWord,
                                              Map<Integer, List<String>> matchesForSecondWord,
                                              int maxWordLength, int wordLength) {

        List<String> firstWords = getMatchedWordsOfLength(matchesForFirstWord, wordLength);
        List<String> secondWords = getMatchedWordsOfLength(matchesForSecondWord, maxWordLength - wordLength);

        for (String firstWord : firstWords) {
            for (String secondWord : secondWords) {
                addCombinedMatchesToAllMatches(firstWord, secondWord);
            }
        }
    }

    private void addCombinedMatchesToAllMatches(String firstWord, String secondWord) {
        String wordSeparator = secondWord.length() > 0 ? "-" : "";
        String postProcessedWord = reinsertUnencodedDigits(firstWord + wordSeparator + secondWord);

        allMatches.add(postProcessedWord);
    }

    private List<String> getMatchedWordsOfLength(Map<Integer, List<String>> matches, int i) {
        if (matches.size() == 0) {
            return Arrays.asList("");
        }
        return matches.get(i);
    }

    private String reinsertUnencodedDigits(String word) {

        Map<Integer, Character> unencodedNumbers = number.getUnencodedDigits();
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
