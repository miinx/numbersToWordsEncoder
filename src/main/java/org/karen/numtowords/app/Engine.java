package org.karen.numtowords.app;

import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.encoder.Encoder;
import org.karen.numtowords.encoder.RegexEncoder;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.io.input.UserInput;
import org.karen.numtowords.io.output.ConsoleOutput;
import org.karen.numtowords.io.output.Output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Engine {

    static final String WELCOME_MESSAGE = "Welcome to the Number to Words Generator.";

    private Input input;
    private Output output;
    private Dictionary dictionary;
    private Encoder encoder;

    private List<String> arguments = new ArrayList<String>();

    public Engine() {
    }

    public void configure(String[] args) throws IOException, FileNotValidException {
        setOutput(new ConsoleOutput());
        setInputAndDictionary(args);
        setEncoder(RegexEncoder.load(dictionary));
    }

    public void writeWelcomeMessage() {
        output.write(WELCOME_MESSAGE);
    }

    public void processNumbers() throws FileNotFoundException {
        while (hasNextNumber()) {
            encodeNumberAndOutputResult();
        }
    }

    void encodeNumberAndOutputResult() throws FileNotFoundException {
        String number = input.getNextNumber();
        List<String> wordMatches = encoder.encode(number);
        output.writeEncodingResults(number, wordMatches);
    }

    boolean hasNextNumber() {
        output.write(input.getNextNumberMessage());
        input.setNextNumber();
        return !Input.EXIT_VALUE.equalsIgnoreCase(input.getNextNumber());
    }

    private void setInputAndDictionary(String[] args) throws IOException, FileNotValidException {
        if (args.length == 0) {
            setInputToUser(UserInput.load());
            dictionary = Dictionary.load(Dictionary.MACOSX_SYSTEM_DICTIONARY_PATH);
        } else {
            processArgs(args);
        }
    }

    private void processArgs(String[] args) throws IOException, FileNotValidException {
        arguments.addAll(Arrays.asList(args));
        List<String> numbersDataFiles = new ArrayList<String>();

        Iterator<String> iterator = arguments.iterator();

        while (iterator.hasNext()) {
            String arg = iterator.next();
            if ("-d".equals(arg)) {
                setUserDictionary(iterator.next());
            } else {
                numbersDataFiles.add(arg);
            }
        }

        if (numbersDataFiles.size() > 0) {
            setInputToFiles(numbersDataFiles);
        }
    }

    private void setUserDictionary(String dictionaryFile) throws IOException, FileNotValidException {
        try {
            dictionary = Dictionary.load(dictionaryFile);
        } catch (Exception e) {
            output.write(e.getMessage());
        }
    }

    void setInputToUser(Input input) {
        this.input = input;
    }

    void setInputToFiles(List<String> files) {
        input = FileInput.loadFiles(files);
    }

    void setOutput(Output output) {
        this.output = output;
    }

    void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    Input getInput() {
        return input;
    }

    Output getOutput() {
        return output;
    }

    Dictionary getDictionary() {
        return dictionary;
    }
}
