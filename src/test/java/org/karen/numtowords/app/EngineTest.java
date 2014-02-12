package org.karen.numtowords.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.io.testdoubles.TestOutput;
import org.karen.numtowords.util.TestUtils;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class EngineTest {

    private Engine engine;
    private TestOutput testOutput = new TestOutput();
    private TestUtils testUtils = new TestUtils();
    private String testDataDir = testUtils.getTestClassesDirectory() + "data/";
    private String[] commandLineArguments;
    String dictionaryFile = testDataDir + "valid-dictionary.txt";
    String inputFile1 = testDataDir + "valid-numbers.txt";
    String inputFile2 = testDataDir + "invalid-numbers.txt";

    @Before
    public void setup(){
        engine = new Engine();
    }

    @Test
    public void displaysWelcomeMessage() {
        engine.setOutput(testOutput);
        engine.writeWelcomeMessage();
        assertThat(testOutput.getOutput(), is(Engine.WELCOME_MESSAGE));
    }


    @Test
    public void setInputToUserInputWhenNoArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[0];

        engine.configure(commandLineArguments);

        assertThat(engine.getInput().getType(), is(Input.Type.USER));
    }

    @Test
    public void setsDictionaryToSystemDictionaryWhenNoArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[0];

        engine.configure(commandLineArguments);

        assertThat(engine.getDictionary().getDictionaryFileName(), is(Dictionary.MACOSX_SYSTEM_DICTIONARY_PATH));
    }

    @Test
    public void setsDictionaryToCustomDictionaryWhenDictionaryArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[] {"-d", dictionaryFile};

        engine.configure(commandLineArguments);

        assertThat(engine.getDictionary().getDictionaryFileName(), is(dictionaryFile));
    }

    @Test
    public void setsInputToFileInputWhenCommandLineFileArgumentGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[] {inputFile1};

        engine.configure(commandLineArguments);

        Input input = engine.getInput();
        assertThat(input.getType(), is(Input.Type.FILE));
    }

    @Test
    public void setsInputToFileInputWhenMultipleCommandLineFileArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[] {inputFile1, inputFile2};

        engine.configure(commandLineArguments);

        Input input = engine.getInput();
        assertThat(input.getType(), is(Input.Type.FILE));

        FileInput fileInput = (FileInput) input;
        assertThat(fileInput.getFileNames().size(), is(2));
        assertThat(fileInput.getFileNames().get(0), is(inputFile1));
        assertThat(fileInput.getFileNames().get(1), is(inputFile2));
    }

    @Test
    public void setsCustomDictionaryAndFileInputTypeWhenBothArgumentTypesGivenWithDictionaryFirst()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{"-d", dictionaryFile, inputFile1};

        engine.configure(commandLineArguments);

        FileInput input = (FileInput) engine.getInput();
        List<String> fileNames = input.getFileNames();
        assertThat(fileNames.size(), is(1));
        assertThat(fileNames.get(0), is(inputFile1));

        assertThat(engine.getDictionary().getDictionaryFileName(), is(dictionaryFile));
    }

    @Test
    public void setsCustomDictionaryAndFileInputTypeWhenBothArgumentTypesGivenWithDictionaryLast()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{inputFile1, "-d", dictionaryFile};

        engine.configure(commandLineArguments);

        FileInput input = (FileInput) engine.getInput();
        List<String> fileNames = input.getFileNames();
        assertThat(fileNames.size(), is(1));
        assertThat(fileNames.get(0), is(inputFile1));

        assertThat(engine.getDictionary().getDictionaryFileName(), is(dictionaryFile));
    }


    @Test
    public void createsEngineForConsoleOutputAsDefault()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[0];

        engine.configure(commandLineArguments);

        assertNotNull(engine.getOutput());
    }


}
