/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.web.exception.OperationNotFoundException;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;

public interface BindingService {

    ServiceInstanceBinding getServiceInstanceBinding(String bindingId, String instanceId) throws ServiceInstanceBindingNotFoundException;

    CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request);

    DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request);

    LastOperationServiceInstanceBindingResponse lastOperation(String bindingId, String instanceId, LastOperationServiceInstanceBindingRequest request) throws OperationNotFoundException;

}
