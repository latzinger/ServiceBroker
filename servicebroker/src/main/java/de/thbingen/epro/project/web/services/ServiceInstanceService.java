package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.GetServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.GetServiceInstanceResponse;

public interface ServiceInstanceService {
    CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request);

    DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request);

    UpdateServiceInstanceRequest updateServiceInstance(UpdateServiceInstanceRequest request);

    GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request);
}
