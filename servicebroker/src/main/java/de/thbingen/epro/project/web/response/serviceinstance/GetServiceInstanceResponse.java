/**
 * Response for retrieving a Service Instance.
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

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetServiceInstanceResponse {

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("dashboard_url")
    private String dashboardUrl;

    @JsonProperty("parameters")
    private Map<String, String> parameters = new HashMap<>();

}
