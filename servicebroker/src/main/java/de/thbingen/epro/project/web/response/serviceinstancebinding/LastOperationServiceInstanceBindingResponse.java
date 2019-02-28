package de.thbingen.epro.project.web.response.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * Representing an LastOperationServiceInstanceBindingResponse containing all necessary information
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LastOperationServiceInstanceBindingResponse {

    @NonNull
    @NotEmpty
    @JsonProperty("state")
    private String state;

    @JsonProperty("description")
    private String description;

}
