package de.thbingen.epro.project.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

/**
 * MappedSuperclass for default Database Entities.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractLongId implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen")
    private Long id;

}
