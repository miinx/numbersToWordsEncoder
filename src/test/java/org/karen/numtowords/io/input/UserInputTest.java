package org.karen.numtowords.io.input;

import org.junit.Before;
import org.junit.Test;
import org.karen.numtowords.validation.DataType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class UserInputTest {

    private UserInput input;

    @Before
    public void setup() {
        input = UserInput.load();
    }

    @Test
    public void identifiesAsUserDataType() {
        assertThat(input.getType(), is(DataType.USER));
    }

    @Test
    public void createsReaderForSystemIn() {
        assertNotNull(input.getReader());
    }

}
