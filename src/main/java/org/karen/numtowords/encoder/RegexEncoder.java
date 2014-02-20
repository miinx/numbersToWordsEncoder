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
        String word;
        int wordLength;

        Map<Integer, List<String>> matchesByWordLengthForWord1 = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> matchesByWordLengthForWord2 = new HashMap<Integer, List<String>>();

        while (dictionaryReader.hasNextLine()) {

            word = dictionaryReader.nextLine();
            wordLength = word.length();

            if (wordLength <= number.getLength()) {
                getMatchesForWordLength(wordLength, number, word, matchesByWordLengthForWord1, 1);
                getMatchesForWordLength(wordLength, number, word, matchesByWordLengthForWord2, 2);
            }
        }

        return buildMatchesList(number, matchesByWordLengthForWord1, matchesByWordLengthForWord2);
    }

    private void getMatchesForWordLength(int wordLength, Number number, String word,
                                         Map<Integer, List<String>> matchesByWordLength, int word1or2) {

        String regex = number.getRegexForRequestedWordAndWordLength(word1or2, wordLength);

        if (word.matches(regex)) {
            getOrSetMatchesForWordLength(matchesByWordLength, wordLength, word);
        }
    }

    private void getOrSetMatchesForWordLength(Map<Integer, List<String>> matchesGroup, int wordLength, String word) {
        if (!matchesGroup.containsKey(wordLength)) {
            matchesGroup.put(wordLength, new ArrayList<String>());
        }
        matchesGroup.get(wordLength).add(word);
    }

    private List<String> buildMatchesList(Number number,
                                          Map<Integer, List<String>> matchesForWord1,
                                          Map<Integer, List<String>> matchesForWord2) {

        List<String> allMatches = new ArrayList<String>();
        boolean matchesExistForWord1;
        boolean matchesExistForWord2;
        int maxWordLength = number.getLength();

        for (int i=1; i<=maxWordLength; i++) {

            matchesExistForWord1 = matchesForWord1.containsKey(i);
            matchesExistForWord2 = matchesForWord2.containsKey(maxWordLength - i);

            if (matchesExistForWord1 || matchesExistForWord2) {
                buildCombinedMatches(number, matchesForWord1, matchesForWord2, allMatches, maxWordLength, i);
            }
        }

        Collections.sort(allMatches);
        return allMatches;
    }

    private void buildCombinedMatches(Number number, Map<Integer, List<String>> matchesForWord1,
                                      Map<Integer, List<String>> matchesForWord2,
                                      List<String> allMatches, int maxWordLength, int i) {
        List<String> word1Words = getWords(matchesForWord1, i);
        List<String> word2Words = getWords(matchesForWord2, maxWordLength - i);

        for (String word1 : word1Words) {
            for (String word2 : word2Words) {
                allCombinedMatchesToAllMatches(number, allMatches, word1, word2);
            }
        }
    }

    private void allCombinedMatchesToAllMatches(Number number, List<String> allMatches, String word1, String word2) {
        String wordSeparator = word2.length() > 0 ? "-" : "";
        String postProcessedWord = reinsertUnencodedDigits(word1 + wordSeparator + word2, number);

        allMatches.add(postProcessedWord);
    }

    private List<String> getWords(Map<Integer, List<String>> matches, int i) {
        if (matches.size() == 0) {
            return Arrays.asList("");
        }
        return matches.get(i);
    }

    private String reinsertUnencodedDigits(String word, Number number) {

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
