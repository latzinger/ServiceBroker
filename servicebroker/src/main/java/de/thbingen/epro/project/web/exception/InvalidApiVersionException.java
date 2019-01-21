/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.exception;

public class InvalidApiVersionException extends RuntimeException{

    public InvalidApiVersionException() {
    }

    public InvalidApiVersionException(String message) {
        super(message);
    }
}