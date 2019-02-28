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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RedisService implements OsbService {

    public static final String SERVICE_ID = "4a30cd4f-914b-4082-8818-e9e9691fa1ce";
    public static final String PLAN_SMALL_ID = "d6a5721a-e022-4bba-8356-6d50692aef27";
    public static final String PLAN_STANDARD_ID = "1bf2507c-7523-4383-bb9d-f79dee50941a";
    public static final String PLAN_CLUSTER_ID = "0b1fbc27-8495-45a6-8858-0e28789026e9";

    @NonNull
    @Autowired
    private RedisInstanceService redisInstanceService;

    @NonNull
    @Autowired
    private RedisInstanceBindingService redisInstanceBindingService;

    @Override
    public ServiceDefinition getServiceDefiniton() {

        ArrayList<Plan> plans = new ArrayList<>();

        plans.add(Plan.builder()
                .id(PLAN_SMALL_ID)
                .name("small")
                .description("single small container with low resources")
                .build());

        plans.add(Plan.builder()
                .id(PLAN_STANDARD_ID)
                .name("standard")
                .description("single container with many resources")
                .build());

        plans.add(Plan.builder()
                .id(PLAN_CLUSTER_ID)
                .name("cluster")
                .description("clusters of containers")
                .build());

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .name("redis")
                .id(SERVICE_ID)
                .description("A simple Redis Server.")
                .bindable(true)
                .plans(plans)
                .build();

        return serviceDefinition;
    }

    @Override
    public String getServiceId() {
        return SERVICE_ID;
    }

    @Override
    public InstanceService getInstanceService() {
        return redisInstanceService;
    }

    @Override
    public BindingService getBindingService() {
        return redisInstanceBindingService;
    }
}
