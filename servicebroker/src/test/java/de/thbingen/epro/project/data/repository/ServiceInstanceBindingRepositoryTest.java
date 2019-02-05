package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.servicebroker.services.redis.RedisService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceInstanceBindingRepositoryTest {

    @Autowired
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    private String bindingId;
    private String instanceId;

    @Before
    public void setUp() throws Exception {
        ServiceInstance serviceInstance =
                new ServiceInstance(RedisService.SERVICE_ID, UUID.randomUUID().toString());

        instanceId = serviceInstance.getId();

        serviceInstanceRepository.save(serviceInstance);

        ServiceInstanceBinding serviceInstanceBinding =
                new ServiceInstanceBinding(serviceInstance);

        bindingId = serviceInstanceBinding.getId();

        serviceInstanceBindingRepository.save(serviceInstanceBinding);

    }

    @Test
    public void testGetServiceInstanceBinding() {

        ServiceInstanceBinding serviceInstanceBinding =
                serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        assertNotNull(serviceInstanceBinding);
        assertTrue(serviceInstanceBinding.getId().equals(bindingId));
        assertTrue(serviceInstanceBinding.getServiceInstance().getId().equals(instanceId));
    }

    @Test
    public void testExistsById() {
        assertTrue(serviceInstanceBindingRepository.existsByIdAndServiceInstance_Id(bindingId, instanceId));
    }

    @After
    public void tearDown() throws Exception {
        serviceInstanceBindingRepository.deleteById(bindingId);
        serviceInstanceRepository.deleteById(instanceId);
    }

}