package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import lombok.Data;
import lombok.NonNull;

@Data
public class ServiceDetails {

    @NonNull
    private Service service;

    public ServiceSpec getSepcs(){
        return service.getSpec();
    }

}
