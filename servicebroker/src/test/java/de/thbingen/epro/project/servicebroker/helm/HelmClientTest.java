package de.thbingen.epro.project.servicebroker.helm;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.helm.exceptions.InstallFailedException;
import de.thbingen.epro.project.servicebroker.helm.exceptions.UninstallFailedException;
import hapi.release.ReleaseOuterClass;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelmClientTest {
    private static final String chartURL = "https://kubernetes-charts.storage.googleapis.com/redis-5.3.0.tgz";
    private final String instanceId = "unit-test-0";
    private ServiceInstance serviceInstance;

    @Autowired
    private HelmClient helmClient;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    @Before
    public void setUp() throws Exception {
        helmClient.installTiller();

        serviceInstance = new ServiceInstance(instanceId, "xxx-service-xxx", "xxx-plan-xxx");
        serviceInstanceRepository.save(serviceInstance);
    }

    @Test
    public void A1_installChart() throws IOException, InstallFailedException {
        Release release = helmClient.installChart(new URL(chartURL), instanceId);

        assertThat(release, notNullValue());
        assertThat("Installation/Initialization failed unexpected", release.isInitialized(), is(true));
    }

    @Test
    public void A2_getServiceDetails() {
        ServiceDetails masterDetails = helmClient.getServiceDetails(instanceId + "-redis-master");
        ServiceDetails slaveDetails = helmClient.getServiceDetails(instanceId + "-redis-slave");
        ServiceDetails nonExistingDetails = helmClient.getServiceDetails(instanceId + "nonExistingId");


        assertNotNull(masterDetails);
        assertNotNull(slaveDetails);
        assertNull(nonExistingDetails);
    }

    @Test
    public void A3_getCredentials() {
        Credentials credentials = helmClient.getCredentials(instanceId + "-redis");
        Credentials nonExistingCredenitals = helmClient.getCredentials(instanceId + "nonExistingId");


        assertNotNull(credentials);
        assertNotNull(credentials.getPassword("redis-password"));
        assertNull(nonExistingCredenitals);
    }

    @Test
    public void A4_uninstallChart() throws IOException, UninstallFailedException {
        Release release = helmClient.uninstallChart(instanceId);

        assertThat(release, notNullValue());
        assertThat("Uninstallation failed unexpected", release.hasDeleted(), is(true));
    }

    @Test
    public void B1_installChartAsync() throws IOException, InterruptedException {
        Operation operation = new Operation(serviceInstance, Operation.OperationState.IN_PROGRESS, "TEST In Progress");
        ChartBuilder chartBuilder = helmClient.loadChart(new URL(chartURL));


        int timeout = 300;
        long start = System.currentTimeMillis();

        helmClient.installChartAsync(chartBuilder, instanceId, timeout, operation);

        boolean finish = false;

        while (!finish && start - System.currentTimeMillis() < timeout * 1000) {
            synchronized (operation) {
                switch (operation.getState()){
                    case SUCCEEDED:
                        finish = true;
                        break;
                    case FAILED:
                        fail();
                        break;
                    case IN_PROGRESS:
                        operation.wait(10000);
                }
            }
        }

        assertTrue(finish);
    }

    @Test
    public void B2_uninstallChartAsync() throws InterruptedException {
        Operation operation = new Operation(serviceInstance, Operation.OperationState.IN_PROGRESS, "TEST In Progress");

        int timeout = 300;
        long start = System.currentTimeMillis();

        helmClient.uninstallChartAsync(instanceId, timeout, operation);

        boolean finish = false;

        while (!finish && start - System.currentTimeMillis() < timeout * 1000) {
            synchronized (operation) {
                switch (operation.getState()){
                    case SUCCEEDED:
                        finish = true;
                        break;
                    case FAILED:
                        fail();
                        break;
                    case IN_PROGRESS:
                        operation.wait(10000);
                }
            }
        }

        assertTrue(finish);
    }
}