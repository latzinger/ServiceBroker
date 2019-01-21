/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.model.services.redis;

import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.*;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.stereotype.Service;

@Service
public class RedisInstanceService implements ServiceInstanceService {


    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public GetServiceInstanceResponse getServiceInstance() {
        return null;
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance() {
        return null;
    }

    @Override
    public LastOperationResponse lastOperation() {
        return null;
    }
}
