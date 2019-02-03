package de.thbingen.epro.project.data.model;

import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.services.redis.RedisService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceInstanceTest {

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    @Test
    @Ignore
    public void testElementCollection(){
        ServiceInstance serviceInstance = new ServiceInstance(RedisService.SERVICE_ID, "000-xxx-000");

        serviceInstance.setServiceId("000-xxx-000");
        serviceInstance.setPlanId("000-yyy-000");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");
        parameters.put("param3", "value3");

        serviceInstance.setParameters(parameters);

        serviceInstanceRepository.save(serviceInstance);
    }
}