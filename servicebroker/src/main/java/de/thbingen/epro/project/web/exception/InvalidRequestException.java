package de.thbingen.epro.project.web.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
    }

    public InvalidRequestException(String s) {
        super(s);
    }
}
