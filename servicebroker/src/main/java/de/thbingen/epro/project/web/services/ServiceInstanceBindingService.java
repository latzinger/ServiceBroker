package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.GetServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationResponse;

public interface ServiceInstanceBindingService {

    CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request);

    DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding();

    GetServiceInstanceBindingResponse getServiceInstanceBinding();

    LastOperationResponse lastOperation();

}
