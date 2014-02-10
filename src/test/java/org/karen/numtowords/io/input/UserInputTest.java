package org.karen.numtowords.io.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserInputTest {

    private UserInput input;

    @Before
    public void setup(){
        input = new UserInput();
    }

    @Test
    public void identifiesAsUserInputType() {
        assertEquals(Input.Type.USER, input.getType());
    }

    @Test
    public void createsReaderForSystemIn() {
        assertNotNull(input.getReader());
    }

}
