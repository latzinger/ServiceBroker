package de.thbingen.epro.project.servicebroker.helm;

import de.thbingen.epro.project.servicebroker.helm.exceptions.InstallFailedException;
import de.thbingen.epro.project.servicebroker.helm.exceptions.UninstallFailedException;
import hapi.chart.ChartOuterClass;
import hapi.chart.ConfigOuterClass;
import hapi.release.ReleaseOuterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class HelmClientTest_custom {
    private static final String chartURL = "https://kubernetes-charts.storage.googleapis.com/redis-5.2.0.tgz";
    private final String instanceId = "redis-cluster-0";

    @Autowired
    private HelmClient helmClient;

    @Before
    public void setUp() throws Exception {
        helmClient.installTiller();
    }

    @Test
    public void A1_installChart() throws IOException, InstallFailedException {
//        ReleaseOuterClass.Release release = helmClient.installChart(new URL(chartURL), instanceId);

        ChartBuilder chart = helmClient.loadChart(new URL(chartURL));

        String rawConf = chart.getChartBuilder().getValues().getRaw();

        Map<String, Object> confMap = new Yaml().load(rawConf);


//        confMap.entrySet().forEach(entry -> System.out.println(entry.getKey() + "\t" + (entry.getValue() != null ? entry.getValue().getClass() + "\t" + entry.getValue(): "")));


        System.out.println("confMap: " + confMap.size());
        System.out.println("===== old =====");
        System.out.println(rawConf);


        Map<String, Object> clusterConf = confMap.get("cluster") instanceof Map? (Map<String, Object>) confMap.get("cluster") : new LinkedHashMap<>();

        clusterConf.put("enabled", true);
        clusterConf.put("slaveCount", 3);


        String raw = new Yaml().dump(confMap);
        System.out.println("======= new =======");
        System.out.println(raw);

        ConfigOuterClass.Config conf = ConfigOuterClass.Config.newBuilder().setRaw(raw).build();

        chart.getChartBuilder().setValues(conf);


        Release release = helmClient.installChart(chart, instanceId);

        assertThat(release, notNullValue());
        assertThat("Installation/Initialization failed, is not initialized", release.isInitialized(), is(true));
    }

    @Test
    public void A2_uninstallChart() throws IOException, UninstallFailedException {
        Release release = helmClient.uninstallChart(instanceId);

        assertThat(release, notNullValue());
        assertThat("Uninstallation failed, has not deleted", release.hasDeleted(), is(true));
    }
}