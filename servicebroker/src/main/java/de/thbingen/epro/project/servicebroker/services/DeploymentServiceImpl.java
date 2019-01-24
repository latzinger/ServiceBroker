package de.thbingen.epro.project.servicebroker.services;


import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.*;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.stereotype.Service;

public abstract class DeploymentServiceImpl implements ServiceInstanceService {

    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request) {
        return null;
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        return null;
    }

    @Override
    public LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request) {
        return null;
    }
}
