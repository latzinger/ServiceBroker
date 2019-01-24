/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.servicebroker.services.ServiceManager;
import de.thbingen.epro.project.web.exception.InvalidApiVersionException;
import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.exception.ServiceNotFoundException;
import de.thbingen.epro.project.web.request.OsbRequest;
import de.thbingen.epro.project.web.response.ErrorMessage;
import de.thbingen.epro.project.web.services.OsbService;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
    private static final String API_VERSION = "2.14";

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ServiceManager serviceManager;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        LOG.error("[Exception]", e.getMessage());
        return getErrorMessageResponseEntity("Exception", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRequestException(InvalidRequestException e) {
        LOG.debug("Invalid request catched", e);
        ResponseEntity<ErrorMessage> invalidRequest = getErrorMessageResponseEntity("InvalidRequest", e.getMessage(), HttpStatus.BAD_REQUEST);
        return invalidRequest;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String message = "Missing required fields:";
        for (FieldError error : result.getFieldErrors()) {
            message += " " + error.getField();
        }
        return getErrorMessageResponseEntity("MissingFields", message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRequestException(ServiceNotFoundException e) {
        LOG.debug("Service " + e.getServiceId() + " not found");

        ResponseEntity<ErrorMessage> serviceNotFound = getErrorMessageResponseEntity("ServiceNotFound", "Service wit id" + e.getServiceId() + " not found", HttpStatus.BAD_REQUEST);
        return serviceNotFound;
    }


    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(String error, String message, HttpStatus status) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(error, message), status);
    }

    public void checkApiVersion(HttpHeaders headers) {
        String apiVersion = headers.toSingleValueMap().get("X-Broker-API-Version");

        if (apiVersion != null) {
            if (apiVersion.compareTo(API_VERSION) != 0) {
                InvalidApiVersionException apiVersionException = new InvalidApiVersionException("API version mismatch: Platform is using ["
                        + "X-Broker-API-Version: " + apiVersion
                        + "] but needed API-Version " + API_VERSION);

                LOG.debug("Invalid API-Version", apiVersionException);
                throw apiVersionException;
            }
        } else {
            LOG.debug("API version missing");
            throw new InvalidApiVersionException("API version missing");
        }
    }

    public void checkAndComplete(HttpHeaders httpHeaders, OsbRequest request) {
        checkAndComplete(httpHeaders, request, new HashMap<>());
    }

    public void checkAndComplete(HttpHeaders httpHeaders, OsbRequest request, Map<String, String> parameters) {
        checkApiVersion(httpHeaders);

        request.setHttpHeaders(httpHeaders.toSingleValueMap());
        request.setParameters(parameters);

        LOG.debug("checkAndComplete successfully");
    }

    protected OsbService getService(String serviceId) {
        OsbService service = serviceManager.getService(serviceId);

        if (service == null)
            throw new ServiceNotFoundException(serviceId);

        return service;
    }
}
