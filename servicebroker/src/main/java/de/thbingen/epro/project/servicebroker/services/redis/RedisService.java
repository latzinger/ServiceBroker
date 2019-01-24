/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.servicebroker.services.BindingService;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.servicebroker.services.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService implements OsbService {

    public static final String SERVICE_ID = "";

    //TODO implement RedisInstanceService
    private RedisInstanceService redisInstanceService;

    //TODO implement RedisBindingService
    private RedisInstanceBindingService redisInstanceBindingService;

    @Autowired
    public RedisService(RedisInstanceService instanceService, RedisInstanceBindingService bindingService) {
        redisInstanceService = instanceService;
        redisInstanceBindingService = bindingService;
    }

    //TODO implement method
    @Override
    public ServiceDefinition getServiceDefiniton() {
        return null;
    }

    @Override
    public ServiceInstanceService getServiceInstanceService() {
        return redisInstanceService;
    }

    @Override
    public BindingService getBindingService() {
        return redisInstanceBindingService;
    }

    @Override
    public String getServiceId() {
        return SERVICE_ID;
    }
}
