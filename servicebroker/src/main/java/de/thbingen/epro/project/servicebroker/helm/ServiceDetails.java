package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.ServicePort;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@Data
public class ServiceDetails {

    @NonNull
    private io.fabric8.kubernetes.api.model.Service service;

    public String getUUID(){
        return service.getMetadata().getUid();
    }

    public String getServiceName(){
        return service.getMetadata().getName();
    }

    public String getClusterName(){
        return service.getMetadata().getClusterName();
    }

    public String getServiceNameSpace(){
        return service.getMetadata().getNamespace();
    }

    public Map<String, String> getServiceLabels(){
        return service.getMetadata().getLabels();
    }

    public List<ServicePort> getServicePorts(){
        return service.getSpec().getPorts();
    }

    public String getServiceType(){
        return service.getSpec().getType();
    }

}
