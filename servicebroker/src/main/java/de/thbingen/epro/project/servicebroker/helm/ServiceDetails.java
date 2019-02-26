package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Service;
import lombok.Data;
import lombok.NonNull;

@Data
public class ServiceDetails {

    @NonNull
    private Service service;

}
