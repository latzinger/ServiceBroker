package de.thbingen.epro.project.data.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Data
public class ServiceInstance extends AbstractEntity{
    private String serviceId;
    private String planId;


}
