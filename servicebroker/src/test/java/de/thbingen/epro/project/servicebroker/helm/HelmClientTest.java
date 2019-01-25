package de.thbingen.epro.project.servicebroker.helm;

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

    @Autowired
    private HelmClient helmClient;

    @Before
    public void setUp() throws Exception {
        helmClient.installTiller();
    }

    @Test
    public void A1_installChart() throws IOException, InstallFailedException {
        ReleaseOuterClass.Release release = helmClient.installChart(new URL(chartURL), instanceId);

        assertThat(release, notNullValue());
        assertThat("Installation/Initialization failed unexpected", release.isInitialized(), is(true));
    }

    @Test
    public void A2_uninstallChart() throws IOException, UninstallFailedException {
        ReleaseOuterClass.Release release = helmClient.uninstallChart(instanceId);

        assertThat(release, notNullValue());
        assertThat("Uninstallation failed unexpected", release.getInfo().hasDeleted(), is(true));
    }
}