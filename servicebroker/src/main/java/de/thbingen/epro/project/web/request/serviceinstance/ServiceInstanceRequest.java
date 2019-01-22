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
