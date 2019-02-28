package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.data.repository.OperationRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceBindingRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.web.exception.OperationNotFoundException;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract InstanceBindingService providing default behavior for a {@link BindingService}.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Slf4j
public abstract class AbstractInstanceBindingService implements BindingService {

    @Autowired
    protected ServiceInstanceBindingRepository serviceInstanceBindingRepository;

    @Autowired
    protected ServiceInstanceRepository serviceInstanceRepository;

    @Autowired
    protected OperationRepository operationRepository;

    @Override
    public abstract CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request);


    @Override
    public abstract DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request);


    /**
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @return {@link ServiceInstanceBinding}
     */
    @Override
    public ServiceInstanceBinding getServiceInstanceBinding(String instanceId, String bindingId) {
        ServiceInstanceBinding serviceInstanceBinding = serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        log.debug("Found binding " + bindingId + " for instance " + instanceId + " : " + (serviceInstanceBinding != null));

        if (serviceInstanceBinding == null)
            throw new ServiceInstanceBindingNotFoundException(bindingId);

        return serviceInstanceBinding;
    }

    /**
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param request    {@link LastOperationServiceInstanceBindingRequest} containing all necessary information
     * @return {@link LastOperationServiceInstanceBindingResponse} containing all necessary information
     * @throws OperationNotFoundException
     */
    @Override
    public LastOperationServiceInstanceBindingResponse lastOperation(String bindingId, String instanceId, LastOperationServiceInstanceBindingRequest request) throws OperationNotFoundException {
        Operation bindingOperation = operationRepository.getBindingOperation(instanceId, bindingId, request.getOperationId());

        if (bindingOperation == null)
            throw new OperationNotFoundException();

        return new LastOperationServiceInstanceBindingResponse(bindingOperation.getState().toString(), bindingOperation.getMessage());
    }

    /**
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @return true if {@link ServiceInstanceBinding} already exists otherwise false.
     */
    protected boolean serviceInstanceBindingExist(String instanceId, String bindingId) {
        return (getServiceInstanceBinding(instanceId, bindingId) != null);
    }

    /**
     * @param serviceInstance        an existing {@link ServiceInstance}
     * @param serviceInstanceBinding an existing {@link ServiceInstanceBinding}
     * @return an new Operation.
     */
    public Operation createOperation(ServiceInstance serviceInstance, ServiceInstanceBinding serviceInstanceBinding) {
        Operation operation = new Operation(serviceInstance, serviceInstanceBinding, Operation.OperationState.IN_PROGRESS, "Operation initialized");
        operationRepository.save(operation);
        return operation;
    }
}
