/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.request.serviceinstance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.Data;
import lombok.NonNull;

@Data
public abstract class ServiceInstanceRequest extends OsbRequest {

    @JsonIgnore
    private String instanceId;

}
