package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DefaultKubernetesClientTest {


    @Test
    public void getServiceDetails() {

        try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient()) {

            Service service = kubernetesClient.services().inNamespace("default").withName("redis-master").get();

            /*
            Secret secret = kubernetesClient.secrets().inNamespace("default").withName("redis").get();
            String passwordEncoded = secret.getData().get("redis-password");
            String passwordDecoded = new String(Base64.getDecoder().decode((String) passwordEncoded));
            System.out.println(passwordDecoded);

            Map<String, String> data = secret.getData();
            data.put("redis-password", new String(Base64.getEncoder().encode(new String("GOQ1yFuMqu").getBytes())));
            secret.setData(data);
            kubernetesClient.secrets().inNamespace("default").withName("redis").replace(secret);

            secret = kubernetesClient.secrets().inNamespace("default").withName("redis").get();
            passwordEncoded = secret.getData().get("redis-password");
            passwordDecoded = new String(Base64.getDecoder().decode((String) passwordEncoded));
            System.out.println(passwordDecoded);
            */


        }

    }

}
