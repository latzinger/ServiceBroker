package de.thbingen.epro.project.web.response.servicebinding;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.thbingen.epro.project.servicebroker.model.VolumeMount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetServiceBindingResponse {

    @JsonProperty("credentials")
    private Map<String, String> credentials = new HashMap<>();

    @JsonProperty("syslog_drain_url")
    private String syslogDrainUrl;

    @JsonProperty("route_service_url")
    private String routeServiceUrl;

    @JsonProperty("volume_mounts")
    private List<VolumeMount> volumeMounts = new ArrayList<>();

    @JsonProperty("parameters")
    private Map<String, String> parameters = new HashMap<>();

}
