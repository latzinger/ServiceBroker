/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.model.services;

import de.thbingen.epro.project.servicebroker.services.ServiceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceManagerTest {

    @Autowired
    private ServiceManager serviceManager;

    @Test
    public void getDefinedServices() {
        assertThat(serviceManager.getDefinedServices().size(), not(0));
    }

    @Test
    public void getService() {

    }
}