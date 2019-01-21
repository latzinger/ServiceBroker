/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */


package de.thbingen.epro.project.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreviousValues implements Serializable {

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("organization_id")
    private String organizationId;

    @JsonProperty("space_id")
    private String spaceId;

}
