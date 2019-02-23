/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.response.serviceinstance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateServiceInstanceResponse {

    @NotEmpty
    @JsonProperty("dashboard_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dashboardUrl;

    @NotEmpty
    @JsonProperty("operation")
    private String operation;
}