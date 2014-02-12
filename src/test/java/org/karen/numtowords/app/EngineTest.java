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
    private String[] commandLineArguments;

    private String dictionaryFile;
    private String dataFile1;
    private String dataFile2;

    @Before
    public void setup()
            throws IOException {

        engine = new Engine();

        dictionaryFile =  TestUtils.createTempFileWithProvidedLines("dictionary", "apple`").getPath();
        dataFile1 =  TestUtils.createTempFileWithProvidedLines("numbers1", "11111").getPath();
        dataFile2 =  TestUtils.createTempFileWithProvidedLines("numbers2", "22222").getPath();
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

        commandLineArguments = new String[]{ dataFile1 };

        engine.configure(commandLineArguments);

        Input input = engine.getInput();
        assertThat(input.getType(), is(Input.Type.FILE));
    }

    @Test
    public void setsInputToFileInputWhenMultipleCommandLineFileArgumentsGiven()
            throws IOException, FileNotValidException {

        commandLineArguments = new String[] {dataFile1, dataFile2};

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

        engine.configure(commandLineArguments);

        assertNotNull(engine.getOutput());
    }


}
