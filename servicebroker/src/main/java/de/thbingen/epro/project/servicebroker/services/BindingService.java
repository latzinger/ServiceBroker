/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.GetServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.GetServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;

public interface BindingService {

    CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request);

    DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request);

    GetServiceInstanceBindingResponse getServiceInstanceBinding(GetServiceInstanceBindingRequest request);

    LastOperationServiceInstanceBindingResponse lastOperation(LastOperationServiceInstanceBindingRequest request);

}
