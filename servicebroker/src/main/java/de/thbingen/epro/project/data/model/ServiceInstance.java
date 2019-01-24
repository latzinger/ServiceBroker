package de.thbingen.epro.project.data.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
public class ServiceInstance extends AbstractEntity {
    @Column
    private String serviceId;

    @Column
    private String planId;

    @Column
    private String dashboardURL;

    @ElementCollection
    @CollectionTable(name = "SERVICE_INSTANCE_PARAMETERS")
    @MapKeyJoinColumn(name = "PARAMETER_KEY")
    @Column(name = "VALUE")
    private Map<String, String> parameters;
}