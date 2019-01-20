package de.thbingen.epro.project.servicebroker.model.services.redis;

import de.thbingen.epro.project.servicebroker.model.ServiceDefinition;
import de.thbingen.epro.project.web.services.Service;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class RedisService implements Service {
    public static final String SERVICE_ID = "";

    @Autowired
    private RedisInstanceService redisInstanceService;

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
