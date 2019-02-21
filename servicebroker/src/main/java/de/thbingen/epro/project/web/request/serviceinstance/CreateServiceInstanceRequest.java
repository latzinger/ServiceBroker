/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.request.serviceinstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateServiceInstanceRequest extends ServiceInstanceRequest {

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

    @JsonProperty("parameters")
    private Map<String, String> parameters;
}
