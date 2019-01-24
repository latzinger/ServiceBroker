package de.thbingen.epro.project.data.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@EqualsAndHashCode
public class AbstractEntity {
    private String id = UUID.randomUUID().toString();
}
