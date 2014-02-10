package org.karen.numtowords.io.input;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.exception.NumbersFileNotFoundException;
import org.karen.numtowords.util.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class FileInputTest {

    private FileInput input;
    private List<String> files;
    private TestUtils testUtils = new TestUtils();
    private String testDataDirectory = testUtils.getTestClassesDirectory() + "data/";

    @Before
    public void setup() {
        files = new ArrayList<String>();
    }

    @Test
    public void createsReaderForExistingFile()
            throws NumbersFileNotFoundException {

        files.add(testDataDirectory + "valid-numbers.txt");

        input = new FileInput(files);
        input.setReader(input.getFileNames().get(0));

        assertNotNull(input.getReader());
        assertEquals(files.get(0), input.getCurrentFile());
    }

    @Test(expected = NumbersFileNotFoundException.class)
    public void throwsExceptionForNonexistentNumbersDataFile()
            throws NumbersFileNotFoundException {

        files.add("does-not-exist.txt");

        input = new FileInput(files);
        input.setReader(input.getFileNames().get(0));
    }

}
