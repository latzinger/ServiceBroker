package de.thbingen.epro.project.data.model;

import de.thbingen.epro.project.data.repository.ServiceInstanceBindingRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.services.redis.RedisService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceInstanceBindingTest {

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    @Autowired
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;

    private String bindingId;
    private String instanceId;

    @Before
    public void setUp() throws Exception {
        ServiceInstance serviceInstance =
                new ServiceInstance(RedisService.SERVICE_ID, UUID.randomUUID().toString());

        instanceId = serviceInstance.getId();

        serviceInstanceRepository.save(serviceInstance);
    }

    @Test
    @Ignore
    public void testElementCollection() {

        ServiceInstance serviceInstance = serviceInstanceRepository.getOne(instanceId);

        assertNotNull(serviceInstance);

        ServiceInstanceBinding serviceInstanceBinding =
                new ServiceInstanceBinding(serviceInstance);

        bindingId = serviceInstanceBinding.getId();

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("uri", "uri");
        credentials.put("username", "username");
        credentials.put("password", "password");
        credentials.put("host", "host");
        credentials.put("port", "port");
        credentials.put("database", "database");

        serviceInstanceBinding.setCredentials(credentials);

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");
        parameters.put("param3", "value3");

        serviceInstanceBinding.setParameters(parameters);

        serviceInstanceBindingRepository.save(serviceInstanceBinding);

        ServiceInstanceBinding binding = serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId,bindingId);

        assertNotNull(binding);
        assertTrue(binding.getId().equals(bindingId));
        assertTrue(binding.getServiceInstance().getId().equals(instanceId));

        System.out.println("Binding: id = " + binding.getId());
        System.out.println("Credentials:");
        binding.getCredentials().forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
        System.out.println("Parameters:");
        binding.getParameters().forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));

    }

    @After
    public void tearDown() throws Exception {
        serviceInstanceBindingRepository.deleteById(bindingId);
        serviceInstanceRepository.deleteById(instanceId);
    }
}