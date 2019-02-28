package de.thbingen.epro.project.web.response.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response data class for updating
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateServiceInstanceResponse {

    @JsonProperty("dashboard_url")
    private String dashboardUrl;

    @JsonProperty("operation")
    private String operation;

}
