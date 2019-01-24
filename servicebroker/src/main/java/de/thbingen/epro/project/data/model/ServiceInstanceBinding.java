package de.thbingen.epro.project.data.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class ServiceInstanceBinding extends AbstractEntity {

    @NonNull
    @ManyToOne
    private ServiceInstance serviceInstance;

    @ElementCollection
    @CollectionTable(name = "SERVICE_INSTANCE_BINDING_CREDENTIALS")
    @MapKeyJoinColumn(name = "CREDENTIALS_KEY")
    @Column(name = "CREDENTIALS_VALUE")
    private Map<String, String> credentials = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "SERVICE_INSTANCE_BINDING_PARAMETERS")
    @MapKeyJoinColumn(name = "PARAMETERS_KEY")
    @Column(name = "PARAMETERS_VALUE")
    private Map<String, String> parameters;

}
