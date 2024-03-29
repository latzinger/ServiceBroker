package de.thbingen.epro.project.web.request.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.thbingen.epro.project.web.schema.PreviousValues;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Request data class for update
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateServiceInstanceRequest extends ServiceInstanceRequest {

    @JsonProperty("context")
    private Map<String, String> context = new HashMap<>();

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("requestParameters")
    private Map<String, String> parameters = new HashMap<>();

    @JsonProperty("previous_values")
    private PreviousValues previousValues;

}
