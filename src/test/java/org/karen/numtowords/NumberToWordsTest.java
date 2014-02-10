package org.karen.numtowords;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class NumberToWordsTest {

    @Test
    public void createsEngine()
            throws IOException {

        NumberToWords app = new NumberToWords();
        String[] args = new String[0];

        app.run(args);

        assertNotNull(app.getEngine());
    }


}
