package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.servicebroker.services.redis.RedisService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceInstanceRepositoryTest {

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    private String instanceId;

    @Before
    public void setUp() throws Exception {
        ServiceInstance serviceInstance =
                new ServiceInstance(RedisService.SERVICE_ID, UUID.randomUUID().toString());

        instanceId = serviceInstance.getId();

        serviceInstanceRepository.save(serviceInstance);
    }

    @Test
    public void testGetServiceInstance() {

        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        assertNotNull(serviceInstance);
        assertTrue(serviceInstance.getId().equals(instanceId));
    }


    @After
    public void tearDown() throws Exception {
        serviceInstanceRepository.deleteById(instanceId);
    }
}