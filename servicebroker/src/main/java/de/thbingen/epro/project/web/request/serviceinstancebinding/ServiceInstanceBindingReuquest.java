package de.thbingen.epro.project.web.request.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.Data;

@Data
public abstract class ServiceInstanceBindingReuquest extends OsbRequest {
    @JsonIgnore
    private String instanceId;

    @JsonIgnore
    private String bindingId;
}
