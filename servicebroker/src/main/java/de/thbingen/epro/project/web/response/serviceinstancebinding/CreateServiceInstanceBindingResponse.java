package de.thbingen.epro.project.web.response.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.thbingen.epro.project.web.schema.VolumeMount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representing an CreateServiceInstanceBindingResponse containing all necessary information
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateServiceInstanceBindingResponse {

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("credentials")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> credentials = new HashMap<>();

    @JsonProperty("syslog_drain_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String syslogDrainUrl;

    @JsonProperty("route_service_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String routeServiceUrl;

    @JsonProperty("volume_mounts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<VolumeMount> volumeMounts = new ArrayList<>();

}
