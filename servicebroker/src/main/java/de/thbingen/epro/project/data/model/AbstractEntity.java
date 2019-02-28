
package de.thbingen.epro.project.data.model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * MappedSuperclass for ServiceInstance and ServiceInstanceBinding Entities.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@MappedSuperclass
@Getter
@EqualsAndHashCode
@NoArgsConstructor
//@RequiredArgsConstructor

public class AbstractEntity {

    @Id
    @NonNull
    private String id = UUID.randomUUID().toString();

    public AbstractEntity(@NonNull String id) {
        this.id = id;
    }
}
