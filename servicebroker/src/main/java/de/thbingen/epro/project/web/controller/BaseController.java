/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.servicebroker.services.ServiceManager;
import de.thbingen.epro.project.web.exception.InvalidApiVersionException;
import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.exception.ServiceNotFoundException;
import de.thbingen.epro.project.web.request.OsbRequest;
import de.thbingen.epro.project.web.response.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public abstract class BaseController {
    private static final String API_VERSION = "2.14";

    @Autowired
    protected ServiceManager serviceManager;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error("[Exception]", e.getMessage());
        e.printStackTrace();
        return getErrorMessageResponseEntity("Exception", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRequestException(InvalidRequestException e) {
        log.debug("Invalid request catched", e);
        ResponseEntity<ErrorMessage> invalidRequest = getErrorMessageResponseEntity("InvalidRequest", e.getMessage(), HttpStatus.BAD_REQUEST);
        return invalidRequest;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        String message = result
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField())
                .collect(Collectors.joining(" "));

        return getErrorMessageResponseEntity("MissingFields", message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleServiceNotFoundException(ServiceNotFoundException e) {
        log.debug("Service " + e.getServiceId() + " not found");

        ResponseEntity<ErrorMessage> serviceNotFound = getErrorMessageResponseEntity("ServiceNotFound", "Service wit id" + e.getServiceId() + " not found", HttpStatus.BAD_REQUEST);
        return serviceNotFound;
    }

    @ExceptionHandler(InvalidApiVersionException.class)
    public ResponseEntity handleInvalidApiVersionException(InvalidApiVersionException ex){
        ResponseEntity<ErrorMessage> invalidApiVersion = getErrorMessageResponseEntity("InvalidApiVersion", ex.getMessage(), HttpStatus.PRECONDITION_FAILED);
        return invalidApiVersion;
    }


    protected ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(String error, String message, HttpStatus status) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(error, message), status);
    }

    protected void checkApiVersion(HttpHeaders headers) throws InvalidApiVersionException {
        List<String> apiVersions = headers.get("X-Broker-API-Version");
        String apiVersion = apiVersions != null && apiVersions.size() == 1 ? apiVersions.get(0) : null;

        if (apiVersion != null) {
            if (!apiVersion.equals(API_VERSION)) {
                InvalidApiVersionException apiVersionException = new InvalidApiVersionException("API version mismatch: Platform is using ["
                        + "X-Broker-API-Version: " + apiVersion
                        + "] but needed API-Version " + API_VERSION);

                log.debug("Invalid API-Version", apiVersionException);
                throw apiVersionException;
            }
        } else {
            log.debug("API version missing");
            throw new InvalidApiVersionException("API version missing");
        }
    }

    protected void checkAndComplete(HttpHeaders httpHeaders, OsbRequest request) {
        checkAndComplete(httpHeaders, request, new HashMap<>());
    }

    protected void checkAndComplete(HttpHeaders httpHeaders, OsbRequest request, Map<String, String> parameters) throws InvalidApiVersionException {
        checkApiVersion(httpHeaders);

        request.setHttpHeaders(httpHeaders.toSingleValueMap());
        request.setRequestParameters(parameters);

        log.debug("checkAndComplete successfully");
    }

    public OsbService getOsbService(String serviceId){
        OsbService service = serviceManager.getService(serviceId);

        log.debug("Found service " + serviceId);
        return service;
    }
}
