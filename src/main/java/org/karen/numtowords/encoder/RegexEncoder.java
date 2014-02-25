package org.karen.numtowords.encoder;

import org.karen.numtowords.dictionary.Dictionary;

import java.util.*;

public class RegexEncoder implements Encoder {

    private Dictionary dictionary;
    private Number number;
    private List<String> result = new ArrayList<String>();

    public static RegexEncoder load(Dictionary dictionary) {
        return new RegexEncoder(dictionary);
    }

    private RegexEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<String> encode(String numberToEncode) {

        number = new Number(numberToEncode);

        Map<Integer, List<String>> matchesForFirstWordByLength = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> matchesForSecondWordByLength = new HashMap<Integer, List<String>>();

        getMatches(matchesForFirstWordByLength, matchesForSecondWordByLength);

        buildResult(matchesForFirstWordByLength, matchesForSecondWordByLength);

        return result;
    }

    private void getMatches(Map<Integer, List<String>> matchesForFirstWordByLength, Map<Integer, List<String>> matchesForSecondWordByLength) {

        Scanner dictionaryReader = dictionary.getReader();
        String word;
        int wordLength;

        while (dictionaryReader.hasNextLine()) {

            word = dictionaryReader.nextLine();
            wordLength = word.length();

            if (wordLength <= number.getMaxWordLength()) {
                getMatchesForWordLength(wordLength, word, matchesForFirstWordByLength, 1);
                getMatchesForWordLength(wordLength, word, matchesForSecondWordByLength, 2);
            }
        }
    }

    private void getMatchesForWordLength(int wordLength, String word,
                                         Map<Integer, List<String>> matchesByWordLength, int whichWord) {

        String regex = number.getRegexForWordWithLength(whichWord, wordLength);

        if (word.matches(regex)) {
            addWordToMatchesByWordLength(matchesByWordLength, wordLength, word);
        }
    }

    private void addWordToMatchesByWordLength(Map<Integer, List<String>> matchesGroup, int wordLength, String word) {
        if (!matchesGroup.containsKey(wordLength)) {
            matchesGroup.put(wordLength, new ArrayList<String>());
        }
        matchesGroup.get(wordLength).add(word);
    }

    private void buildResult(Map<Integer, List<String>> matchesForFirstWord, Map<Integer, List<String>> matchesForSecondWord) {
        int maxWordLength = number.getMaxWordLength();

        for (int wordLength = 1; wordLength <= maxWordLength; wordLength++) {
            if (matchesForFirstWord.containsKey(wordLength)) {
                buildMatchesForCombinedWords(matchesForFirstWord, matchesForSecondWord, maxWordLength, wordLength);
            }
        }

        Collections.sort(result);
    }

    private void buildMatchesForCombinedWords(Map<Integer, List<String>> matchesForFirstWord,
                                              Map<Integer, List<String>> matchesForSecondWord,
                                              int maxWordLength, int wordLength) {

        List<String> firstWords = getMatchedWordsOfLength(matchesForFirstWord, wordLength);
        List<String> secondWords = getMatchedWordsOfLength(matchesForSecondWord, maxWordLength - wordLength);

        for (String firstWord : firstWords) {
            if (secondWords.size() == 0) {
                addFirstWordToResult(firstWord);
            } else {
                addCombinedWordsToResult(secondWords, firstWord);
            }
        }
    }

    private List<String> getMatchedWordsOfLength(Map<Integer, List<String>> matches, int i) {
        List<String> matchesOfGivenLength = matches.get(i);
        if (matches.size() == 0 || matchesOfGivenLength == null) {
            return Collections.emptyList();
        }
        return matchesOfGivenLength;
    }

    private void addFirstWordToResult(String firstWord) {
        if (firstWord.length() == number.getMaxWordLength()) {
            String postProcessedWord = reinsertUnencodedDigits(firstWord);
            addWordToResult(postProcessedWord);
        }
    }

    private void addCombinedWordsToResult(List<String> secondWords, String firstWord) {
        for (String secondWord : secondWords) {
            String wordSeparator = secondWord.length() > 0 ? "-" : "";
            String postProcessedWord = reinsertUnencodedDigits(firstWord + wordSeparator + secondWord);

            addWordToResult(postProcessedWord);
        }
    }

    private String reinsertUnencodedDigits(String word) {

        Map<Integer, Character> unencodedNumbers = number.getUnencodedDigits();
        int indexOfWordSeparator = word.indexOf("-");

        StringBuilder stringBuilder = new StringBuilder(word);
        Iterator<Map.Entry<Integer, Character>> iterator = unencodedNumbers.entrySet().iterator();
        Map.Entry<Integer, Character> pair;
        Integer position;

        while (iterator.hasNext()) {
            pair = iterator.next();
            position = pair.getKey();
            if (position >= indexOfWordSeparator) {
                position += 1;
            }
            stringBuilder.insert(position, pair.getValue().toString());
        }

        return stringBuilder.toString().toUpperCase();
    }

    private void addWordToResult(String postProcessedWord) {
        if (!result.contains(postProcessedWord)) {
            result.add(postProcessedWord);
        }
    }
}
