package de.thbingen.epro.project.servicebroker.services;


import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.*;

public abstract class AbstractInstanceService implements InstanceService {

    @Override
    public abstract CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request);

    @Override
    public abstract UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request);

    @Override
    public abstract DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request);

    @Override
    public abstract LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request);
}
