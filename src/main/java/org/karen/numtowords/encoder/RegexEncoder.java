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

        Scanner dictionaryReader = dictionary.getReader();
        number = new Number(numberToEncode);

        Map<Integer, List<String>> matchesByWordLengthForWord1 = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> matchesByWordLengthForWord2 = new HashMap<Integer, List<String>>();

        String word;
        int wordLength;

        while (dictionaryReader.hasNextLine()) {

            word = dictionaryReader.nextLine();
            wordLength = word.length();

            if (wordLength <= number.getLength()) {
                getMatchesForWordLength(wordLength, word, matchesByWordLengthForWord1, 1);
                getMatchesForWordLength(wordLength, word, matchesByWordLengthForWord2, 2);
            }
        }

        buildListOfAllMatches(matchesByWordLengthForWord1, matchesByWordLengthForWord2);

        return allMatches;
    }

    private void getMatchesForWordLength(int wordLength, String word,
                                         Map<Integer, List<String>> matchesByWordLength, int whichWord) {

        String regex = number.getRegexForRequestedWordWithWordLength(whichWord, wordLength);

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

    private void buildListOfAllMatches(Map<Integer, List<String>> matchesForWord1, Map<Integer, List<String>> matchesForWord2) {
        boolean matchesExistForWord1WithLength;
        boolean matchesExistForWord2WithLength;
        int maxWordLength = number.getLength();

        for (int wordLength=1; wordLength<=maxWordLength; wordLength++) {
            matchesExistForWord1WithLength = matchesForWord1.containsKey(wordLength);
            matchesExistForWord2WithLength = matchesForWord2.containsKey(maxWordLength - wordLength);

            // todo: if matchesExistForWord2 only, is this logic still ok?
            if (matchesExistForWord1WithLength || matchesExistForWord2WithLength) {
                buildCombinedMatches(matchesForWord1, matchesForWord2, maxWordLength, wordLength);
            }
        }

        Collections.sort(allMatches);
    }

    private void buildCombinedMatches(Map<Integer, List<String>> matchesForWord1, Map<Integer, List<String>> matchesForWord2,
                                      int maxWordLength, int wordLength) {
        List<String> word1Words = getMatchedWordsOfLength(matchesForWord1, wordLength);
        List<String> word2Words = getMatchedWordsOfLength(matchesForWord2, maxWordLength - wordLength);

        for (String word1 : word1Words) {
            for (String word2 : word2Words) {
                addCombinedMatchesToAllMatches(word1, word2);
            }
        }
    }

    private void addCombinedMatchesToAllMatches(String word1, String word2) {
        String wordSeparator = word2.length() > 0 ? "-" : "";
        String postProcessedWord = reinsertUnencodedDigits(word1 + wordSeparator + word2);

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
