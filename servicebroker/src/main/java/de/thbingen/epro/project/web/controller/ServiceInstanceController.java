/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.OperationRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.services.InstanceService;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.web.exception.*;
import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.LastOperationServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.UpdateServiceInstanceResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v2/service_instances")
@Slf4j
@RequiredArgsConstructor
public class ServiceInstanceController extends BaseController {
    @NonNull
    private ServiceInstanceRepository serviceInstanceRepository;


    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        instanceId = formatInstanceId(instanceId);
        checkApiVersion(httpHeaders);

        ServiceInstance serviceInstance = getServiceInstance(instanceId);

        return ResponseEntity.ok(serviceInstance);
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody CreateServiceInstanceRequest request) throws ServiceInstanceAlreadyExistsException {
        instanceId = formatInstanceId(instanceId);
        checkAndComplete(httpHeaders, request, instanceId, parameters);

        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);
        log.debug("Found serviceInstance with id " + instanceId + " : " + (serviceInstance != null));

        if (serviceInstance != null) {
            if (serviceInstance.getParameters().equals(request.getParameters())) {
                if (serviceInstance.isInitialized())
                    return ResponseEntity.ok(null);
                else
                    return ResponseEntity.accepted().body(null);
            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        InstanceService instanceService = getInstanceService(request.getServiceId());
        CreateServiceInstanceResponse createResponse = instanceService.createServiceInstance(request);

        log.debug("createService returns");
        return ResponseEntity.ok(createResponse);
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        instanceId = formatInstanceId(instanceId);
        DeleteServiceInstanceRequest request = new DeleteServiceInstanceRequest();
        checkAndComplete(httpHeaders, request, instanceId, parameters);
        checkSerivceIdAndPlanId(request);


        try {
            InstanceService instanceService = getInstanceService(request);
            DeleteServiceInstanceResponse response = instanceService.deleteServiceInstance(request);
            return ResponseEntity.ok(response);
        } catch (ServiceInstanceNotFoundException e) {
            checkAcceptIncomplete(request);
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody UpdateServiceInstanceRequest request) {
        instanceId = formatInstanceId(instanceId);
        checkAndComplete(httpHeaders, request, instanceId, parameters);

        InstanceService instanceService = getInstanceService(request);

        UpdateServiceInstanceResponse response = instanceService.updateServiceInstance(request);

        //TODO implement method
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) throws OperationNotFoundException {
        instanceId = formatInstanceId(instanceId);
        LastOperationServiceInstanceRequest request = new LastOperationServiceInstanceRequest();
        checkAndComplete(httpHeaders, request, instanceId, parameters);

        String operationId = parameters.get("operation");
        if (operationId == null)
            throw new InvalidRequestException();
        request.setOperationId(operationId);


        ServiceInstance serviceInstance = getServiceInstance(instanceId);

        Operation operation = getOperation(instanceId, operationId);
        InstanceService instanceService = getInstanceService(request);


        LastOperationServiceInstanceResponse response = instanceService.lastOperation(request);
        return ResponseEntity.ok(response);
    }

    private void checkAndComplete(HttpHeaders httpHeaders, ServiceInstanceRequest request, String instanceId, Map<String, String> parameters) {
        super.checkAndComplete(httpHeaders, request, parameters);

        request.setInstanceId(instanceId);
    }

    private ServiceInstance getServiceInstance(String instanceId) throws ServiceInstanceNotFoundException {
        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);
        if (serviceInstance == null)
            throw new ServiceInstanceNotFoundException(instanceId);

        return serviceInstance;
    }

    private InstanceService getInstanceService(ServiceInstanceRequest request) {
        ServiceInstance serviceInstance = getServiceInstance(request.getInstanceId());

        String serviceId = serviceInstance.getServiceId();

        return getInstanceService(serviceId);
    }

    private InstanceService getInstanceService(String serviceId) {
        OsbService osbService = getOsbService(serviceId);

        return osbService.getInstanceService();
    }

    @SuppressWarnings("Duplicates")
    public void checkAcceptIncomplete(ServiceInstanceRequest request) {
        Map<String, String> parameters = request.getRequestParameters();
        String accepts_incomplete = parameters.get("accepts_incomplete");

        log.debug("accepts_incomplete: " + accepts_incomplete);

        if (!Boolean.parseBoolean(accepts_incomplete))
            throw new RequiresAccpetsIncompleteException();
    }

    @ExceptionHandler(ServiceInstanceNotFoundException.class)
    private ResponseEntity<?> handleServiceInstanceNotFoundException(ServiceInstanceNotFoundException e) {
        log.debug("ServiceInstance not found: " + e.getInstanceId());
        return ResponseEntity.status(HttpStatus.GONE).build();
    }


    public void checkSerivceIdAndPlanId(ServiceInstanceRequest request) {
        String serviceId = request.getRequestParameters().get("service_id");
        String planId = request.getRequestParameters().get("plan_id");

        if (serviceId == null || planId == null) {
            throw new InvalidRequestException("service_id or plan_id not provided");
        }
    }
}
