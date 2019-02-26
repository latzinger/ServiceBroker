package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DefaultKubernetesClientTest {


    @Test
    public void getServiceDetails() {

        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient()) {

            Service service = kubernetesClient.services().inNamespace("default").withName("helm-default").get();


        }

    }

}
