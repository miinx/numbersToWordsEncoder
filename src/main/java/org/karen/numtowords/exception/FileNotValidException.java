package org.karen.numtowords.exception;

public class FileNotValidException extends Exception {

    public FileNotValidException(String s) {
        super(s);
    }

    public FileNotValidException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
