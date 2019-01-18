/**
 * Base controller, containing default Exception Handling.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.web.exception.InvalidApiVersionException;
import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.response.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class BaseController {
    private static final String API_VERSION = "2.13";

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        LOG.error("[Exception]", e.getMessage());
        return getErrorMessageResponseEntity("Exception", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(String error, String message, HttpStatus status) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(error, message), status);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRequestException(InvalidRequestException e) {
        LOG.debug("Invalid request catched", e);
        ResponseEntity<ErrorMessage> invalidRequest = getErrorMessageResponseEntity("InvalidRequest", e.getMessage(), HttpStatus.BAD_REQUEST);
        return invalidRequest;
    }

    public void checkRequestValidity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Request has " + bindingResult.getErrorCount() + " invalid fields");
        }
    }

    public void checkApiVersion(String apiVersion) {
        if (apiVersion.compareTo(API_VERSION) != 0)
            throw new InvalidApiVersionException("API version mismatch: Platform is using ["
                    + "X-Broker-API-Version: " + apiVersion
                    + "] but needed API-Version " + API_VERSION);
    }
}
