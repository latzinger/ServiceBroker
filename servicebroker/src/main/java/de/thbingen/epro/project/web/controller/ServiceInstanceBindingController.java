/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.data.repository.ServiceInstanceBindingRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.services.BindingService;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.web.exception.*;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.ErrorMessage;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;
import lombok.extern.slf4j.Slf4j;
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
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;
    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> getServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceNotFoundException {

        checkApiVersion(httpHeaders);
        BindingService bindingService = getBindingService(instanceId, bindingId);

        ServiceInstanceBinding serviceInstanceBinding =
                bindingService.getServiceInstanceBinding(instanceId, bindingId);

        /*
         * check if binding in progress (lastOperation) and throw 404 Not Found
         */

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

        if (serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId) == null)
            return new ResponseEntity<>(HttpStatus.GONE);

        BindingService bindingService = getBindingService(instanceId, bindingId);

        DeleteServiceInstanceBindingRequest request =
                new DeleteServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setRequestParameters(parameters);

        DeleteServiceInstanceBindingResponse response =
                bindingService.deleteServiceInstanceBinding(bindingId, instanceId, request);

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
        request.setRequestParameters(parameters);

        BindingService bindingService = getBindingService(instanceId, bindingId);

        ServiceInstanceBinding serviceInstanceBinding =
                serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        if (serviceInstanceBinding != null) {
            if (serviceInstanceBinding.getParameters().equals(request.getParameters()))
                return new ResponseEntity<>(serviceInstanceBinding, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        CreateServiceInstanceBindingResponse response =
                bindingService.createServiceInstanceBinding(bindingId, instanceId, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private BindingService getBindingService(String instanceId, String bindingId) {
        ServiceInstance serviceInstance = getServiceInstance(instanceId, bindingId);
        OsbService service = getOsbService(serviceInstance.getServiceId());
        return service.getBindingService();
    }

    private ServiceInstance getServiceInstance(String instanceId, String bindingId) {
        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        if (serviceInstance == null)
            throw new ServiceInstanceBindingNotFoundException(bindingId);

        return serviceInstance;
    }

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException, OperationNotFoundException {

        checkApiVersion(httpHeaders);

        LastOperationServiceInstanceBindingRequest request =
                new LastOperationServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setRequestParameters(parameters);

        BindingService bindingService = getBindingService(instanceId, bindingId);
        LastOperationServiceInstanceBindingResponse response = bindingService.lastOperation(bindingId, instanceId, request);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ServiceInstanceBindingNotFoundException.class)
    private ResponseEntity<?> handleServiceInstanceBindingNotFoundException(ServiceInstanceBindingNotFoundException e) {
        log.debug(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceInstanceBindingBadRequestException.class)
    private ResponseEntity<ErrorMessage> handleServiceInstanceBindingBadRequestException(ServiceInstanceBindingBadRequestException e) {
        log.debug(e.getMessage());
        return getErrorMessageResponseEntity("ServiceInstanceBindingBadRequestException", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
