package org.karen.numtowords.exception;

import java.io.IOException;

public class DictionaryNotFoundException extends IOException {

    public DictionaryNotFoundException(String s) {
        super(s);
    }

    public DictionaryNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
