package de.thbingen.epro.project.web.exception;

public class InvalidApiVersionException extends RuntimeException{

    public InvalidApiVersionException() {
    }

    public InvalidApiVersionException(String message) {
        super(message);
    }
}