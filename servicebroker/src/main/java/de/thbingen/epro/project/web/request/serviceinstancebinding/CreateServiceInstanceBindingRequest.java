/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.request.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.thbingen.epro.project.servicebroker.model.BindResource;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class CreateServiceInstanceBindingRequest extends OsbRequest {

    @JsonProperty("context")
    private Map<String, String> context = new HashMap<>();

    @NonNull
    @NotEmpty
    @JsonProperty("service_id")
    private String serviceId;

    @NonNull
    @NotEmpty
    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("app_guid")
    private String appGuid;

    @JsonProperty("bind_resource")
    private BindResource bindResource;

    @JsonProperty("parameters")
    private Map<String, String> parameters = new HashMap<>();

}
