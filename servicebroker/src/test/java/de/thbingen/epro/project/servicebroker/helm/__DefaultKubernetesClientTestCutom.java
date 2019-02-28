/*
 * Developed by Jonas Hueg (jhueg) on 26.02.19 15:50.
 * Last modified 26.02.19 15:50.
 * Copyright (c) 2019. All rights reserved.
 */

package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Endpoints;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import org.junit.Ignore;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Ignore
public class __DefaultKubernetesClientTestCutom {


    @Test
    public void listAll() throws UnknownHostException {
        try (DefaultKubernetesClient client = new DefaultKubernetesClient()) {
            List<Endpoints> endpoints = client.endpoints().list().getItems();

            for (Endpoints e : endpoints) {
//                System.out.println(e);
            }

            List<Node> nodes = client.nodes().list().getItems();

//            for (Node node : nodes) {
//                System.out.println(node);
//                node.getStatus().getAddresses().stream().forEach(System.out::println);
//            }

//            List<Service> items = client.services().list().getItems();
//            items.stream().forEach(service -> service.getSpec().getPorts().stream()
//                    .forEach(servicePort -> System.out.println(servicePort.getNodePort())));

//            client.services().list().getItems().stream().forEach(System.out::println);

            Service service = client.services().inNamespace("default").withName("abc-123-def-redis-master").get();

            System.out.println(service);

            List<String> externalIPs = service.getSpec().getExternalIPs();
            externalIPs.stream().forEach(System.out::println);

            String masterUrl = client.getConfiguration().getMasterUrl();
            InetAddress minikube = Inet4Address.getByName("minikube");
            System.out.println(minikube.getHostAddress());
            System.out.println(masterUrl);
        }
    }
}
