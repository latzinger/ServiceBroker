package de.thbingen.epro.project.servicebroker.model.services.redis;

import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.GetServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.GetServiceInstanceResponse;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.stereotype.Service;

@Service
public class RedisInstanceService implements ServiceInstanceService {
    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        return null;
    }

    @Override
    public UpdateServiceInstanceRequest updateServiceInstance(UpdateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request) {
        return null;
    }
}
