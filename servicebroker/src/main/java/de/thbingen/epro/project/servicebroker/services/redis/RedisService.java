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
import de.thbingen.epro.project.web.schema.Plan;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.servicebroker.services.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RedisService implements OsbService {

    public static final String SERVICE_ID = "4a30cd4f-914b-4082-8818-e9e9691fa1ce";

    //TODO implement RedisInstanceService
    private RedisInstanceService redisInstanceService;

    @Autowired
    public RedisService(RedisInstanceService instanceService) {
        redisInstanceService = instanceService;
    }

    @Override
    public ServiceDefinition getServiceDefiniton() {

        ArrayList<Plan> plans = new ArrayList<>();

        plans.add(Plan.builder()
                .id("d6a5721a-e022-4bba-8356-6d50692aef27")
                .name("small")
                .description("single small container with low resources")
                .build());

        plans.add(Plan.builder()
                .id("1bf2507c-7523-4383-bb9d-f79dee50941a")
                .name("standard")
                .description("single container with many resources")
                .build());

        plans.add(Plan.builder()
                .id("0b1fbc27-8495-45a6-8858-0e28789026e9")
                .name("cluster")
                .description("clusters of containers")
                .build());

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .name("Redis")
                .id(SERVICE_ID)
                .description("A simple Redis Server.")
                .bindable(true)
                .plans(plans)
                .build();

        return serviceDefinition;
    }

    @Override
    public InstanceService getInstanceService() {
        return redisInstanceService;
    }

    @Override
    public String getServiceId() {
        return SERVICE_ID;
    }
}
