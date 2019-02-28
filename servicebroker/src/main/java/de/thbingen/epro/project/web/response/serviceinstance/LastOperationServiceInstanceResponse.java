
package de.thbingen.epro.project.web.response.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * Request data class for get last operation
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
@AllArgsConstructor
@Builder
public class LastOperationServiceInstanceResponse {

    @NonNull
    @NotEmpty
    @JsonProperty("state")
    private String state;

    @JsonProperty("description")
    private String description;

}
