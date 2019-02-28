/*
 * Developed by Jonas Hueg (jhueg) on 28.02.19 18:35.
 * Last modified 27.02.19 22:34.
 * Copyright (c) 2019. All rights reserved.
 */

/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.postgres;

import de.thbingen.epro.project.servicebroker.services.BindingService;
import de.thbingen.epro.project.servicebroker.services.InstanceService;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.web.schema.Plan;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostgresService implements OsbService {

    public static final String SERVICE_ID = "5989ec65-f04f-438d-9889-b5aec3c49bf4";
    public static final String PLAN_SMALL_ID = "c7bc888e-8732-4e08-be25-5eb91d4b1589";
    public static final String PLAN_STANDARD_ID = "085a6c92-ffa0-4901-a011-a7a8bd85dddc";
    public static final String PLAN_CLUSTER_ID = "22f63649-983d-46d3-a921-f91ee16a14c1";

    @NonNull
    @Autowired
    private PostgresInstanceService postgresInstanceService;

    @NonNull
    @Autowired
    private PostgresInstanceBindingService postgresInstanceBindingService;

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
                .name("postgres")
                .id(SERVICE_ID)
                .description("A simple Postgres Server.")
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
        return postgresInstanceService;
    }

    @Override
    public BindingService getBindingService() {
        return postgresInstanceBindingService;
    }
}
