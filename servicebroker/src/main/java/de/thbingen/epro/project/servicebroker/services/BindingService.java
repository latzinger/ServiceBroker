
package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.web.exception.OperationNotFoundException;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;

/**
 * Interface defining an BindingService.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

public interface BindingService {

    /**
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @return {@link ServiceInstanceBinding}
     * @throws ServiceInstanceBindingNotFoundException
     */
    ServiceInstanceBinding getServiceInstanceBinding(String bindingId, String instanceId) throws ServiceInstanceBindingNotFoundException;

    /**
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param request    {@link CreateServiceInstanceBindingRequest} containing all necessary information
     * @return {@link ServiceInstanceBinding}
     */
    CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request);

    /**
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param request    {@link DeleteServiceInstanceBindingRequest} containing necessary information
     * @return {@link DeleteServiceInstanceBindingResponse}
     */
    DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request);

    /**
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param request    {@link LastOperationServiceInstanceBindingRequest} containing all necessary information
     * @return {@link LastOperationServiceInstanceBindingResponse} containing all necessary information
     * @throws OperationNotFoundException
     */
    LastOperationServiceInstanceBindingResponse lastOperation(String bindingId, String instanceId, LastOperationServiceInstanceBindingRequest request) throws OperationNotFoundException;

}
