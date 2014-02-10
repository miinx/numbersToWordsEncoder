package org.karen.numtowords;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class NumberToWordsTest {

    private NumberToWords app;
    private String[] emptyArgs = new String[0];

    @Test
    public void createsEngine() {
        app = new NumberToWords();
        app.run(emptyArgs);
        assertNotNull(app.getEngine());
    }


}
