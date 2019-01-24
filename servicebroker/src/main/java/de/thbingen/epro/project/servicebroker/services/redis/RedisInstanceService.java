/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.servicebroker.services.AbstractServiceInstanceService;
import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.*;
import de.thbingen.epro.project.servicebroker.services.ServiceInstanceService;
import org.springframework.stereotype.Service;

@Service
public class RedisInstanceService extends AbstractServiceInstanceService {


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
