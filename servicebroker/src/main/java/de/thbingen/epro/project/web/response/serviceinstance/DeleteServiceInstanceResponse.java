package de.thbingen.epro.project.web.response.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response data class for unprovisioning
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteServiceInstanceResponse {

    @JsonProperty("operation")
    private String operation;

}
