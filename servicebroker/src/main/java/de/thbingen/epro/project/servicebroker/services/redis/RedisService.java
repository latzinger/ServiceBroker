/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.web.model.ServiceDefinition;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService implements de.thbingen.epro.project.web.services.Service {

    public static final String SERVICE_ID = "";

    //TODO implement RedisInstanceService
    private RedisInstanceService redisInstanceService;

    //TODO implement RedisBindingService
    private RedisInstanceBindingService redisInstanceBindingService;

    @Autowired
    public RedisService(RedisInstanceService instanceService, RedisInstanceBindingService bindingService){
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
    public String getServiceId() {
        return SERVICE_ID;
    }
}
