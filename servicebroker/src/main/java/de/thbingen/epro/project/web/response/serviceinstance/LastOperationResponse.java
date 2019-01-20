/**
 * Response for last operation.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.response.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class LastOperationResponse {

    @NonNull
    @NotEmpty
    @JsonProperty("state")
    private String state;

    @JsonProperty("description")
    private String description;

}
