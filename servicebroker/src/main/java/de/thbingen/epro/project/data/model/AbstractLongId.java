/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

@MappedSuperclass
@Data
@SequenceGenerator(name = "seq_gen", sequenceName = "hibernate_sequence")
public abstract class AbstractLongId implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_gen")
    private Long id;

}
