/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.data.service.ServiceInstanceBindingService;
import de.thbingen.epro.project.data.service.ServiceInstanceService;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public abstract class AbstractInstanceBindingService implements BindingService {

    @Autowired
    protected ServiceInstanceBindingService serviceInstanceBindingService;

    @Override
    public abstract CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request);

    @Override
    public abstract DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request);

    @Override
    public abstract LastOperationServiceInstanceBindingResponse lastOperation(LastOperationServiceInstanceBindingRequest request);

    public ServiceInstanceBinding getServiceInstanceBinding(String instanceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException {
        return serviceInstanceBindingService.getServiceInstanceBinding(instanceId, bindingId);
    }
}
