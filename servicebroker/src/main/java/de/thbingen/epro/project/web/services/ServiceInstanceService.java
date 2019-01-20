package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.*;

public interface ServiceInstanceService {

    CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request);

    UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request);

    GetServiceInstanceResponse getServiceInstance();

    DeleteServiceInstanceResponse deleteServiceInstance();

    LastOperationResponse lastOperation();

}
