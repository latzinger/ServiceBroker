/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.model.services;

import de.thbingen.epro.project.servicebroker.services.BindingService;
import de.thbingen.epro.project.servicebroker.services.ServiceManager;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import de.thbingen.epro.project.servicebroker.services.OsbService;
import de.thbingen.epro.project.servicebroker.services.InstanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceManagerTest {

    @Autowired
    private ServiceManager serviceManager;

    @Test
    public void getDefinedServices() {
        List<? extends OsbService> definedServices = serviceManager.getDefinedServices();

        assertThat(definedServices, notNullValue());
        assertThat(definedServices.size(), not(0));

        System.out.printf("There are %d defined services:%n", definedServices.size());

        definedServices.forEach(System.out::println);
    }

    @Test
    public void getService() {
        OsbService service = serviceManager.getService(TestService.SERVICE_ID);

        assertThat(service, instanceOf(TestService.class));
    }
}

@Service
class TestService implements OsbService{
    public static final String SERVICE_ID = "000-xxx-000";
    @Override
    public ServiceDefinition getServiceDefiniton() {
        return null;
    }

    @Override
    public String getServiceId() {
        return SERVICE_ID;
    }

    @Override
    public InstanceService getInstanceService() {
        return null;
    }

    @Override
    public BindingService getBindingService() {
        return null;
    }
}