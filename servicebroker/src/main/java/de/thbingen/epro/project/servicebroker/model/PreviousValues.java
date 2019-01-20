package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreviousValues {

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("organization_id")
    private String organizationId;

    @JsonProperty("space_id")
    private String spaceId;

}
