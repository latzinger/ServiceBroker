

package de.thbingen.epro.project.servicebroker.services;


import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.OperationRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.exception.RequiresAcceptsIncompleteException;
import de.thbingen.epro.project.web.exception.ServiceInstanceAlreadyExistsException;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import de.thbingen.epro.project.web.request.OsbRequest;
import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.LastOperationServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.UpdateServiceInstanceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for implementing {@link InstanceService} with default behavior
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */
@Log4j2
public abstract class AbstractInstanceService implements InstanceService {

    @Autowired
    protected ServiceInstanceRepository serviceInstanceRepository;

    @Autowired
    protected OperationRepository operationRepository;

    @Override
    public abstract CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) throws ServiceInstanceAlreadyExistsException;

    @Override
    public abstract UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request);

    @Override
    public abstract DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request);

    @Override
    public abstract LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request);


    /**
     * Get {@link ServiceInstance} or throw {@link ServiceInstanceNotFoundException}
     * @param instanceId
     * @return
     * @throws ServiceInstanceNotFoundException
     */
    protected ServiceInstance getServiceInstance(String instanceId) throws ServiceInstanceNotFoundException {
        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);
        if (serviceInstance == null)
            throw new ServiceInstanceNotFoundException(instanceId);
        return serviceInstance;
    }

    /**
     * Check if ServiceInstance exists
     * @param instanceId
     * @return
     */
    protected boolean serviceInstanceExists(String instanceId) {
        try {
            getServiceInstance(instanceId);
        } catch (ServiceInstanceNotFoundException e) {
            return false;
        }

        return true;
    }

    /**
     * Creates a ServiceInstance in the database
     * @param request
     * @return
     * @throws ServiceInstanceAlreadyExistsException
     */
    public ServiceInstance createServiceInstanceEntry(CreateServiceInstanceRequest request) throws ServiceInstanceAlreadyExistsException {
        if (serviceInstanceExists(request.getInstanceId())) {
            log.debug("InstanceId already exists");
            throw new ServiceInstanceAlreadyExistsException();
        }

        ServiceInstance serviceInstance = new ServiceInstance(request.getInstanceId(), request.getServiceId(), request.getPlanId());
        Map<String, Object> parameters = request.getParameters();
        Map<String, String> parameterStrings = new HashMap<>();

        if (parameters != null)
            parameters.forEach((s, o) -> parameterStrings.put(s, o != null ? o.toString() : null));

        serviceInstance.setParameters(parameterStrings);

        serviceInstanceRepository.save(serviceInstance);
        return serviceInstance;
    }

    /**
     * Creates an {@link Operation} and save it with state {@link Operation.OperationState#IN_PROGRESS}
     * @param serviceInstance
     * @return
     */
    public Operation createOperation(ServiceInstance serviceInstance) {
        Operation operation = new Operation(serviceInstance, Operation.OperationState.IN_PROGRESS, "Operation initialized");
        operationRepository.save(operation);
        return operation;
    }

    /**
     * Checks if request has request parameter accepts_incomplete and is set to true else throws {@link RequiresAcceptsIncompleteException}
     * @param request
     */
    public void checkAcceptIncomplete(ServiceInstanceRequest request) {
        Map<String, String> parameters = request.getRequestParameters();
        String accepts_incomplete = parameters.get("accepts_incomplete");

        log.debug("accepts_incomplete: " + accepts_incomplete);

        if (!Boolean.parseBoolean(accepts_incomplete))
            throw new RequiresAcceptsIncompleteException();
    }

    /**
     * Checks if request parameters service_id and plan_id are provided and match with ServiceInstance saved in database
     * @param request
     */
    public void checkSerivceIdAndPlanId(ServiceInstanceRequest request) {
        ServiceInstance serviceInstance = getServiceInstance(request.getInstanceId());

        String serviceId = request.getRequestParameters().get("service_id");
        String planId = request.getRequestParameters().get("plan_id");

        if (serviceId == null || planId == null || !serviceId.equals(serviceInstance.getServiceId()) || !planId.equals(serviceInstance.getPlanId())) {
            throw new InvalidRequestException("service_id or plan_id not provided or does not match instanceId");
        }
    }

//
//    public void checkPlanExists(OsbRequest request) {
//
//    }

}
