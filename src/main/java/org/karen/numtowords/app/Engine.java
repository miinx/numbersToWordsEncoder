package org.karen.numtowords.app;

import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.io.input.UserInput;
import org.karen.numtowords.io.output.ConsoleOutput;
import org.karen.numtowords.io.output.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine {

    // todo: check all getters & setters are needed (other classes too)

    public static final String WELCOME_MESSAGE = "Welcome to the Number to Words Generator.";

    private Input input;
    private Output output;
    private Dictionary dictionary;

    public Engine() {
    }

    public void configure(String[] args)
            throws IOException, FileNotValidException {

        processArgs(args);
        setOutput(new ConsoleOutput());

        // todo: success messages for files loaded
    }

    public void writeWelcomeMessage() {
        output.write(WELCOME_MESSAGE);
    }

    private void processArgs(String[] args)
            throws IOException, FileNotValidException {

        if (args.length == 0) {
            input = UserInput.load();
            dictionary = Dictionary.load(Dictionary.MACOSX_SYSTEM_DICTIONARY_PATH);

        } else {
            parseArgs(args);
        }
    }

    private void parseArgs(String[] args)
            throws IOException, FileNotValidException {

        List<String> arguments = new ArrayList<String>();
        arguments.addAll(Arrays.asList(args));

        List<String> numbersDataFiles = new ArrayList<String>();

        while (arguments.size() > 0) {

            if (arguments.get(0).equals("-d")) {
                List<String> dictionaryArgs = arguments.subList(0, 2);

                setDictionary(dictionaryArgs);

                // todo: arguments.removeAll(dictionaryArgs) throws ConcurrentModificationException.. better way?
                arguments.remove(0);
                arguments.remove(0);

            } else {
                numbersDataFiles.add(arguments.remove(0));
            }

        }

        if (numbersDataFiles.size() > 0) {
            setInput(numbersDataFiles);
        }
    }

    // todo test output messages are correct for caught exceptions
    private void setDictionary(List<String> dictionaryArgs)
            throws IOException, FileNotValidException {

        String userDictionary = dictionaryArgs.get(1);

        try {
            dictionary = Dictionary.load(userDictionary);

        } catch (Exception e) {
            output.write(e.getMessage());
        }
    }

    private void setInput(List<String> arguments) {
        input = FileInput.loadFiles(arguments);
    }

    void setOutput(Output output) {
        this.output = output;
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
