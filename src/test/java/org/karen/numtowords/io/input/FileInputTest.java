package org.karen.numtowords.io.input;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.exception.FileNotValidException;
import org.karen.numtowords.util.TestUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class FileInputTest {

    private FileInput input;
    private List<String> files;

    @Before
    public void setup() {
        files = new ArrayList<String>();
    }

    @Test
    public void createsReaderForExistingFile()
            throws IOException, FileNotValidException {

        String validFile = TestUtils.createTempFileWithProvidedLines("numbers", "123").getPath();
        files.add(validFile);

        input = FileInput.loadFiles(files);
        input.setReader(input.getFilePaths().get(0));

        assertNotNull(input.getReader());
        assertEquals(files.get(0), input.getCurrentFile());
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsExceptionForNonexistentNumbersDataFile()
            throws IOException, FileNotValidException {

        files.add("does-not-exist.txt");

        input = FileInput.loadFiles(files);
        input.setReader(input.getFilePaths().get(0));
    }

    @Test(expected = FileNotValidException.class)
    public void throwsExceptionForInvalidNumbersDataFileContainingWords()
            throws IOException, FileNotValidException {

        String invalidFile = TestUtils.createTempFileWithProvidedLines("test", "foo").getPath();
        files.add(invalidFile);

        input = FileInput.loadFiles(files);
        input.setReader(input.getFilePaths().get(0));
    }

}
