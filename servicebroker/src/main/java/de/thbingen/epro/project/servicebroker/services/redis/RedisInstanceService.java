/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.servicebroker.services.AbstractInstanceService;
import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.LastOperationServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.LastOperationServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.UpdateServiceInstanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisInstanceService extends AbstractInstanceService {

    @Autowired
    private RedisService redisService;

    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
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
