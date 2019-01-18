package de.thbingen.epro.project.web.request.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CreateServiceInstanceRequest {
    @NotNull
    @NotEmpty
    @NonNull
    @JsonProperty("service_id")
    private String serviceId;

    @NotNull
    @NotEmpty
    @NonNull
    @JsonProperty("plan_id")
    private String planId;

    @NotNull
    @NotEmpty
    @NonNull
    @JsonProperty("organization_guid")
    private String organizationGuid;

    @NotNull
    @NotEmpty
    @NonNull
    @JsonProperty("space_guid")
    private String spaceGuid;


    private Map<String, String> parameters;
}
