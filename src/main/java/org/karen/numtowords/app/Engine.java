package org.karen.numtowords.app;

import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.DictionaryNotFoundException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.io.input.UserInput;
import org.karen.numtowords.io.output.ConsoleOutput;
import org.karen.numtowords.io.output.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine {

    public static final String WELCOME_MESSAGE = "Welcome to the Number to Words Generator.";

    private Input input;
    private Output output;
    private Dictionary dictionary;

    public Engine() {
    }

    public void configure(String[] args) {
        setInputAndDictionary(args);
        setOutput(new ConsoleOutput());
    }

    public void writeWelcomeMessage() {
        output.write(WELCOME_MESSAGE);
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Output getOutput() {
        return output;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    private void setInputAndDictionary(String[] args) {
        List<String> arguments = new ArrayList<String>();
        arguments.addAll(Arrays.asList(args));

        if (arguments.contains("-d")) {
            int dictionaryArgIndex = arguments.indexOf("-d");
            setUserDictionary(arguments, dictionaryArgIndex + 1);

            arguments.subList(dictionaryArgIndex, dictionaryArgIndex + 2).clear();

        } else {
            setDictionary(Dictionary.MACOSX_SYSTEM_DICTIONARY_PATH);
        }

        setInputType(arguments);
    }

    private void setUserDictionary(List<String> arguments, int dictionaryIndex) {
        String userDictionaryPath = arguments.get(dictionaryIndex);
        setDictionary(userDictionaryPath);
    }

    private void setDictionary(String dictionaryPath) {
        try {
            dictionary = new Dictionary(dictionaryPath);
        } catch (DictionaryNotFoundException e) {
            output.write(e.getMessage());
        }
    }

    private void setInputType(List<String> arguments) {
        if (arguments.size() == 0) {
            setInput(new UserInput());
        } else {
            setInput(new FileInput(arguments));
        }
    }

}
