package de.thbingen.epro.project.web.response.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representing an DeleteServiceInstanceBindingResponse containing all necessary information
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteServiceInstanceBindingResponse {

    @JsonProperty("operation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operation;

}
