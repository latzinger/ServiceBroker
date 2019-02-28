package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServicePort;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

/**
 * Storing Kubernetes Service for specified Service.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
public class ServiceDetails {

    @NonNull
    private Service service;

    /**
     * @return UUID of the specified Service.
     */
    public String getUUID() {
        return service.getMetadata().getUid();
    }

    /**
     * @return Name of the specified Service.
     */
    public String getServiceName() {
        return service.getMetadata().getName();
    }

    /**
     * @return Namespace of the specified Service.
     */
    public String getServiceNameSpace() {
        return service.getMetadata().getNamespace();
    }

    /**
     * @return Map containing all Labels of the specified Service.
     */
    public Map<String, String> getServiceLabels() {
        return service.getMetadata().getLabels();
    }

    /**
     * @return List containing all Ports of the specified Service.
     */
    public List<ServicePort> getServicePorts() {
        return service.getSpec().getPorts();
    }

    /**
     * @return Type of the specified Service (ClusterIP, NodePort, LoadBalancer).
     */
    public String getServiceType() {
        return service.getSpec().getType();
    }

}
