package de.thbingen.epro.project.servicebroker.services;


import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.*;

public abstract class AbstractServiceInstanceService implements ServiceInstanceService {

    @Override
    public abstract CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request);

    @Override
    public abstract UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request);

    @Override
    public abstract GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request);

    @Override
    public abstract DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request);

    @Override
    public abstract LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request);
}
