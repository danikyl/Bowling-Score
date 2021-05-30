package com.technology.daniel.javachallenge.exception;

public class FileWrongFormatException extends RuntimeException {

    public FileWrongFormatException() {
        super();
    }

    public FileWrongFormatException(final String message) {
        super(message);
    }
}
