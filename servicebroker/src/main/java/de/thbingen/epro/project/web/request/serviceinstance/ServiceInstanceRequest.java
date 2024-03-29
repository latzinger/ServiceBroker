
package de.thbingen.epro.project.web.request.serviceinstance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.Data;
import lombok.NonNull;


/**
 * Abstract base class for all {@link OsbRequest} related to serviceinstance
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
public abstract class ServiceInstanceRequest extends OsbRequest {

    @JsonIgnore
    private String instanceId;

}
