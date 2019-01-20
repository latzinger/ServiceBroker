/**
 * Response for updating a Service Instance.
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
import lombok.NoArgsConstructor;

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
