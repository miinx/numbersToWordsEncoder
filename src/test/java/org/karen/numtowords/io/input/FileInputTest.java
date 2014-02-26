package org.karen.numtowords.io.input;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


public class FileInputTest {

    private FileInput input;
    private List<String> files;

    @Before
    public void setup() {
        files = new ArrayList<String>();
    }

    @Test
    public void createsReaderForExistingFile() throws IOException, FileNotValidException {
        String validFile = TestUtils.createTempFileWithProvidedLines("numbers", "123").getPath();
        files.add(validFile);

        input = FileInput.loadFiles(files);

        assertNotNull(input.getReader());
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsExceptionForNonexistentNumbersDataFile() throws IOException, FileNotValidException {
        files.add("does-not-exist.txt");

        input = FileInput.loadFiles(files);
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidNumbersDataFileContainingWords() throws IOException, FileNotValidException {
        String invalidFile = TestUtils.createTempFileWithProvidedLines("test", "foo").getPath();
        files.add(invalidFile);

        input = FileInput.loadFiles(files);
    }

    @Test
    public void getsNextNumberInCurrentFile() throws IOException, FileNotValidException {
        String validFile = TestUtils.createTempFileWithProvidedLines("numbers", "123").getPath();
        files.add(validFile);

        input = FileInput.loadFiles(files);

        input.setNextNumber();

        assertThat(input.getNextNumber(), is("123"));
    }

    @Test
    public void getsNextNumberFromNextFileWhenCurrentFileHasNoMoreLines() throws IOException, FileNotValidException {
        String file1 = TestUtils.createTempFileWithProvidedLines("numbers1").getPath();
        String file2 = TestUtils.createTempFileWithProvidedLines("numbers2", "456").getPath();
        files.add(file1);
        files.add(file2);

        input = FileInput.loadFiles(files);

        input.setNextNumber();

        assertThat(input.getNextNumber(), is("456"));
    }

    @Test
    public void setsNextNumberToExitValueWhenNoMoreFilesWithNumbers() throws IOException, FileNotValidException {
        String file1 = TestUtils.createTempFileWithProvidedLines("numbers1").getPath();
        String file2 = TestUtils.createTempFileWithProvidedLines("numbers2").getPath();
        files.add(file1);
        files.add(file2);

        input = FileInput.loadFiles(files);

        input.setNextNumber();

        assertThat(input.getNextNumber(), is(Input.EXIT_VALUE));
    }
}
