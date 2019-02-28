package de.thbingen.epro.project.web.exception;

/**
 * InvalidRequestException thrown if missing request data or request data is malformed
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
    }

    public InvalidRequestException(String s) {
        super(s);
    }
}
