package org.karen.numtowords.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.karen.numtowords.dictionary.Dictionary;
import org.karen.numtowords.encoder.RegexEncoder;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.io.input.FileInput;
import org.karen.numtowords.io.input.Input;
import org.karen.numtowords.io.input.UserInput;
import org.karen.numtowords.io.output.ConsoleOutput;
import org.karen.numtowords.io.testdoubles.TestOutput;
import org.karen.numtowords.util.TestUtils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EngineTest {

    private Engine engine;

    @Mock
    private ConsoleOutput consoleOutput;
    @Mock
    private UserInput userInput;
    @Mock
    private FileInput fileInput;
    @Mock
    private Dictionary dictionary;
    @Mock
    private RegexEncoder encoder;

    private String[] commandLineArguments;
    private String dictionaryFile;
    private String dataFile1;
    private String dataFile2;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(userInput.getType()).thenReturn(Input.Type.USER);
        when(userInput.getNextNumberMessage()).thenReturn("Enter next number:");

        engine = new Engine();

        dictionaryFile = TestUtils.createTempFileWithProvidedLines("dictionary", "apple").getPath();
        dataFile1 = TestUtils.createTempFileWithProvidedLines("numbers1", "11111").getPath();
        dataFile2 = TestUtils.createTempFileWithProvidedLines("numbers2", "22222").getPath();
    }

    @Test
    public void displaysWelcomeMessage() {
        TestOutput testOutput = new TestOutput();
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

        assertThat(engine.getDictionary().getDictionaryFile().getPath(), is(Dictionary.MACOSX_SYSTEM_DICTIONARY_PATH));
    }

    @Test
    public void setsDictionaryToCustomDictionaryWhenDictionaryArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{"-d", dictionaryFile};

        engine.configure(commandLineArguments);

        assertThat(engine.getDictionary().getDictionaryFile().getPath(), is(dictionaryFile));
    }

    @Test
    public void setsInputToFileInputWhenCommandLineFileArgumentGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{dataFile1};

        engine.configure(commandLineArguments);

        Input input = engine.getInput();
        assertThat(input.getType(), is(Input.Type.FILE));
    }

    @Test
    public void setsInputToFileInputWhenMultipleCommandLineFileArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{dataFile1, dataFile2};

        engine.configure(commandLineArguments);

        Input input = engine.getInput();
        assertThat(input.getType(), is(Input.Type.FILE));

        FileInput fileInput = (FileInput) input;
        assertThat(fileInput.getFilePaths().size(), is(2));
        assertThat(fileInput.getFilePaths().get(0), is(dataFile1));
        assertThat(fileInput.getFilePaths().get(1), is(dataFile2));
    }

    @Test
    public void setsCustomDictionaryAndFileInputTypeWhenBothArgumentTypesGivenWithDictionaryFirst()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{"-d", dictionaryFile, dataFile1};

        engine.configure(commandLineArguments);

        FileInput input = (FileInput) engine.getInput();
        List<String> fileNames = input.getFilePaths();
        assertThat(fileNames.size(), is(1));
        assertThat(fileNames.get(0), is(dataFile1));

        assertThat(engine.getDictionary().getDictionaryFile().getPath(), is(dictionaryFile));
    }

    @Test
    public void setsCustomDictionaryAndFileInputTypeWhenBothArgumentTypesGivenWithDictionaryLast()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[]{dataFile1, "-d", dictionaryFile};

        engine.configure(commandLineArguments);

        FileInput input = (FileInput) engine.getInput();
        List<String> fileNames = input.getFilePaths();
        assertThat(fileNames.size(), is(1));
        assertThat(fileNames.get(0), is(dataFile1));

        assertThat(engine.getDictionary().getDictionaryFile().getPath(), is(dictionaryFile));
    }


    @Test
    public void createsEngineForConsoleOutputAsDefault()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[0];

        // use a new engine for this test, to avoid setting the output to TestOutput in before()
        engine = new Engine();
        engine.configure(commandLineArguments);

        assertNotNull(engine.getOutput());
    }

    @Test
    public void exitsWhenExitValueIsEnteredOnCommandLine() {
        engine.setOutput(consoleOutput);
        engine.setInputToUser(userInput);

        when(userInput.getNextNumber()).thenReturn("quit");

        boolean hasNextNumber = engine.hasNextNumber();

        assertThat(hasNextNumber, is(false));
    }

    @Test
    public void setsNextNumberWhenUserEntersAnythingButExitValue() {
        engine.setOutput(consoleOutput);
        engine.setInputToUser(userInput);

        when(userInput.getNextNumber()).thenReturn("123");

        boolean hasNextNumber = engine.hasNextNumber();

        assertThat(hasNextNumber, is(true));
    }

    @Test
    public void writesEncodingResultForProvidedNumber() throws FileNotFoundException {
        engine.setOutput(consoleOutput);
        engine.setInputToUser(userInput);
        engine.setEncoder(encoder);

        String number = "234";
        List<String> results = Arrays.asList("CALL");

        when(userInput.getNextNumber()).thenReturn(number);
        when(encoder.encode(anyString())).thenReturn(results);

        engine.encodeNumberAndOutputResult();

        verify(encoder).encode(number);
        verify(consoleOutput).writeEncodingResults(number, results);
    }

}
