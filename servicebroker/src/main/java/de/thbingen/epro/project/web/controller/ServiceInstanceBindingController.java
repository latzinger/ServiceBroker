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

/**
 * ServiceInstanceBinding Controller.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/v2/service_instances")
@Slf4j
public class ServiceInstanceBindingController extends BaseController {

    @Autowired
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;
    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    /**
     * REST mapping for fetching an {@link ServiceInstanceBinding}
     *
     * @param httpHeaders
     * @param instanceId
     * @param bindingId
     * @return
     * @throws InvalidApiVersionException
     * @throws ServiceInstanceBindingNotFoundException
     * @throws ServiceNotFoundException
     */
    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> getServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceNotFoundException {

        instanceId = formatInstanceId(instanceId);

        log.debug("GET: /v2/service_instances/" + instanceId + "/service_bindings/" + bindingId + " getServiceInstanceBinding()");

        checkApiVersion(httpHeaders);
        BindingService bindingService = getBindingService(instanceId, bindingId);

        ServiceInstanceBinding serviceInstanceBinding =
                bindingService.getServiceInstanceBinding(instanceId, bindingId);


        return new ResponseEntity<>(serviceInstanceBinding, HttpStatus.OK);
    }

    /**
     * REST mapping for deleting an {@link ServiceInstanceBinding}
     *
     * @param httpHeaders
     * @param instanceId
     * @param bindingId
     * @param parameters
     * @return
     * @throws InvalidApiVersionException
     * @throws ServiceInstanceBindingNotFoundException
     * @throws ServiceInstanceNotFoundException
     * @throws ServiceNotFoundException
     */
    @DeleteMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> deleteServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException {

        instanceId = formatInstanceId(instanceId);

        log.debug("DELETE: /v2/service_instances/" + instanceId + "/service_bindings/" + bindingId + " deleteServiceInstanceBinding()");

        checkApiVersion(httpHeaders);

        String accepts_incomplete = parameters.get("accepts_incomplete");
        if (!Boolean.parseBoolean(accepts_incomplete))
            throw new RequiresAcceptsIncompleteException();

        if (parameters.get("service_id") == null || parameters.get("plan_id") == null)
            throw new InvalidRequestException("service_id or plan_id not provided");

        if (serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId) == null)
            return new ResponseEntity<>(HttpStatus.GONE);

        DeleteServiceInstanceBindingRequest request =
                new DeleteServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setRequestParameters(parameters);

        BindingService bindingService = getBindingService(instanceId, bindingId);

        DeleteServiceInstanceBindingResponse response =
                bindingService.deleteServiceInstanceBinding(bindingId, instanceId, request);

        if (response.getOperation() == null)
            return new ResponseEntity<>("{}", HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * REST mapping for creating an {@link ServiceInstanceBinding}
     *
     * @param httpHeaders
     * @param instanceId
     * @param bindingId
     * @param parameters
     * @param request
     * @return
     * @throws InvalidApiVersionException
     * @throws InvalidRequestException
     * @throws ServiceInstanceBindingNotFoundException
     * @throws ServiceInstanceNotFoundException
     * @throws ServiceNotFoundException
     */
    @PutMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> createServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody CreateServiceInstanceBindingRequest request)
            throws InvalidApiVersionException, InvalidRequestException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException {

        instanceId = formatInstanceId(instanceId);

        log.debug("PUT: /v2/service_instances/" + instanceId + "/service_bindings/" + bindingId + " createServiceInstanceBinding()");

        checkApiVersion(httpHeaders);

        if (request.getServiceId() == null || request.getPlanId() == null)
            throw new InvalidRequestException("service_id or plan_id not provided");

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

    /**
     * REST mapping for retrieving last {@link de.thbingen.epro.project.data.model.Operation}
     *
     * @param httpHeaders
     * @param instanceId
     * @param bindingId
     * @param parameters
     * @return
     * @throws InvalidApiVersionException
     * @throws ServiceInstanceBindingNotFoundException
     * @throws ServiceInstanceNotFoundException
     * @throws ServiceNotFoundException
     * @throws OperationNotFoundException
     */
    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters)
            throws InvalidApiVersionException, ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException, ServiceNotFoundException, OperationNotFoundException {

        instanceId = formatInstanceId(instanceId);

        log.debug("GET: /v2/service_instances/" + instanceId + "/service_bindings/" + bindingId + " lastOperation()");

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

    /**
     * Returning BindingService of a concrete Service Broker Service
     *
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @return {@link BindingService} of concrete Service.
     */
    private BindingService getBindingService(String instanceId, String bindingId) {
        ServiceInstance serviceInstance = getServiceInstance(instanceId, bindingId);
        OsbService service = getOsbService(serviceInstance.getServiceId());
        return service.getBindingService();
    }

    /**
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param bindingId  binding_if which should be used
     * @return {@link ServiceInstance} to given instanceId
     */
    private ServiceInstance getServiceInstance(String instanceId, String bindingId) {
        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        if (serviceInstance == null)
            throw new ServiceInstanceBindingNotFoundException(bindingId);

        return serviceInstance;
    }

}
