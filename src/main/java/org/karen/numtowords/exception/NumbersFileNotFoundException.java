package org.karen.numtowords.exception;

import java.io.IOException;

public class NumbersFileNotFoundException extends IOException {

    public NumbersFileNotFoundException(String s) {
        super(s);
    }

    public NumbersFileNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
