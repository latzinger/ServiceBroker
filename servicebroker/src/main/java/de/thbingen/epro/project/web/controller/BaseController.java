package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.repository.OperationRepository;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.servicebroker.services.ServiceManager;
import de.thbingen.epro.project.web.exception.*;
import de.thbingen.epro.project.web.request.OsbRequest;
import de.thbingen.epro.project.web.response.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Defining default behavior of a REST-Controller.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Log4j2
public abstract class BaseController {
    private static final String API_VERSION = "2.14";

    @Autowired
    protected ServiceManager serviceManager;

    @Autowired
    protected OperationRepository operationRepository;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error("[Unhandled Exception]", e.getMessage(), e);
//        e.printStackTrace();
        return getErrorMessageResponseEntity("Exception", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRequestException(InvalidRequestException e) {
        log.debug("Invalid request catched", e.getMessage());
        return getErrorMessageResponseEntity("InvalidRequest", e.getMessage(), HttpStatus.BAD_REQUEST);
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

        return getErrorMessageResponseEntity("ServiceNotFound", "Service wit id" + e.getServiceId() + " not found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidApiVersionException.class)
    public ResponseEntity handleInvalidApiVersionException(InvalidApiVersionException ex) {
        return getErrorMessageResponseEntity("InvalidApiVersion", ex.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity handleOperationNotFoundException(OperationNotFoundException ex) {
        return getErrorMessageResponseEntity("OperationNotFound", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequiresAcceptsIncompleteException.class)
    public ResponseEntity handleRequiresAccpetsIncompleteException(RequiresAcceptsIncompleteException ex) {
        return getErrorMessageResponseEntity(ErrorMessage.ASYNC_REQUIRED, ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleJsonMappingException(HttpMessageNotReadableException ex) {
        log.debug("Catched HttpMessageNotReadableException: " + ex.getMessage());
        return getErrorMessageResponseEntity("Missing or invalid fields: " + ex.getMessage(), ex.getMessage(), HttpStatus.BAD_REQUEST);
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

    public OsbService getOsbService(String serviceId) {
        OsbService service = serviceManager.getService(serviceId);

        log.debug("Found service " + service.getClass());
        return service;
    }

    public Operation getOperation(String instanceId, String operationId) throws OperationNotFoundException {
        Long id = -1L;

        try {
            id = Long.parseLong(operationId);
        } catch (NumberFormatException e) {
            id = -1L;
            log.debug("Provided id " + operationId + " is not a valid operation id (a Long)");
        }

        Operation operation = operationRepository.getOperation(instanceId, id);

        if (operation == null)
            throw new OperationNotFoundException();

        return operation;
    }
}
