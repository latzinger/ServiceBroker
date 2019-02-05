/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.servicebroker.services.AbstractInstanceBindingService;
import de.thbingen.epro.project.servicebroker.services.BindingService;
import de.thbingen.epro.project.web.exception.*;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.ErrorMessage;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v2/service_instances")
@Slf4j
public class ServiceInstanceBindingController extends BaseController {


    @Autowired
    private AbstractInstanceBindingService bindingService;

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> getServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException {

        checkApiVersion(httpHeaders);

        ServiceInstanceBinding serviceInstanceBinding =
                bindingService.getServiceInstanceBinding(instanceId, bindingId);

        return new ResponseEntity<>(serviceInstanceBinding, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> deleteServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException {

        checkApiVersion(httpHeaders);

        DeleteServiceInstanceBindingRequest request =
                new DeleteServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setParameters(parameters);

        DeleteServiceInstanceBindingResponse response =
                bindingService.deleteServiceInstanceBinding(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> createServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody CreateServiceInstanceBindingRequest request)
            throws InvalidApiVersionException, InvalidRequestException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException {

        checkApiVersion(httpHeaders);
        request.setHttpHeaders(httpHeaders.toSingleValueMap());
        request.setParameters(parameters);

        CreateServiceInstanceBindingResponse response =
                bindingService.createServiceInstanceBinding(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException {

        checkApiVersion(httpHeaders);

        LastOperationServiceInstanceBindingRequest request =
                new LastOperationServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setParameters(parameters);

        LastOperationServiceInstanceBindingResponse response =
                bindingService.lastOperation(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ServiceInstanceBindingNotFoundException.class)
    private ResponseEntity<ErrorMessage> handleServiceInstanceBindingNotFoundException(ServiceInstanceBindingNotFoundException e) {
        log.debug(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ServiceInstanceBindingDoesNotExistException.class)
    private ResponseEntity<ErrorMessage> handleServiceInstanceBindingNotFoundException(ServiceInstanceBindingDoesNotExistException e) {
        log.debug(e.getMessage());
        return getErrorMessageResponseEntity("ServiceInstanceBindingNotFoundException", e.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(ServiceInstanceBindingBadRequestException.class)
    private ResponseEntity<ErrorMessage> handleServiceInstanceBindingNotFoundException(ServiceInstanceBindingBadRequestException e) {
        log.debug(e.getMessage());
        return getErrorMessageResponseEntity("ServiceInstanceBindingNotFoundException", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
