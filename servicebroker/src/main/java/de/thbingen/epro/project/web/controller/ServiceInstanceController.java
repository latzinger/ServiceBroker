/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.servicebroker.services.AbstractInstanceService;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.LastOperationServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.UpdateServiceInstanceResponse;
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
@RequestMapping("/v2/service_instance")
@Slf4j
public class ServiceInstanceController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private AbstractInstanceService instanceService;

    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        checkApiVersion(httpHeaders);

        ServiceInstance serviceInstance = getServiceInstance(instanceId);

        return ResponseEntity.ok(serviceInstance);
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody CreateServiceInstanceRequest request) {
        checkAndComplete(httpHeaders, request, instanceId, parameters);

        ServiceInstance serviceInstance = instanceService.getServiceInstance(instanceId);
        if (serviceInstance != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        CreateServiceInstanceResponse createResponse = instanceService.createServiceInstance(request);

        return ResponseEntity.ok(createResponse);
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        DeleteServiceInstanceRequest request = new DeleteServiceInstanceRequest();
        checkAndComplete(httpHeaders, request, parameters);

        ServiceInstance serviceInstance = getServiceInstance(instanceId);

        DeleteServiceInstanceResponse response = instanceService.deleteServiceInstance(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody UpdateServiceInstanceRequest request) {
        checkAndComplete(httpHeaders, request, instanceId, parameters);

        ServiceInstance serviceInstance = getServiceInstance(instanceId);

        UpdateServiceInstanceResponse response = instanceService.updateServiceInstance(request);

        //TODO implement method
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        LastOperationServiceInstanceRequest request = new LastOperationServiceInstanceRequest();
        checkAndComplete(httpHeaders, request, instanceId, parameters);

        ServiceInstance serviceInstance = getServiceInstance(instanceId);

        LastOperationServiceInstanceResponse response = instanceService.lastOperation(request);

        return ResponseEntity.ok(response);
    }

    private void checkAndComplete(HttpHeaders httpHeaders, ServiceInstanceRequest request, String instanceId, Map<String, String> parameters) {
        super.checkAndComplete(httpHeaders, request, parameters);

        request.setInstanceId(instanceId);
    }

    private ServiceInstance getServiceInstance(String instanceId) throws ServiceInstanceNotFoundException {
        ServiceInstance serviceInstance = instanceService.getServiceInstance(instanceId);
        if (serviceInstance == null)
            throw new ServiceInstanceNotFoundException(instanceId);

        return serviceInstance;
    }

    @ExceptionHandler(ServiceInstanceNotFoundException.class)
    private ResponseEntity<?> handleServiceInstanceNotFoundException(ServiceInstanceNotFoundException e) {
        LOG.debug("ServiceInstance not found: " + e.getInstanceId());
        return ResponseEntity.notFound().build();
    }

}
