/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.request.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.thbingen.epro.project.web.model.PreviousValues;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class UpdateServiceInstanceRequest extends OsbRequest {

    @JsonProperty("context")
    private Map<String, String> context = new HashMap<>();

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("parameters")
    private Map<String, String> parameters = new HashMap<>();

    @JsonProperty("previous_values")
    private PreviousValues previousValues;

}