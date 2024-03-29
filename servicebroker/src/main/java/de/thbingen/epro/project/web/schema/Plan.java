package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Representing Plan defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Plan implements Serializable {

    @NonNull
    @NotEmpty
    @JsonProperty("id")
    private String id;

    @NonNull
    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NonNull
    @NotEmpty
    @JsonProperty("description")
    private String description;

    @JsonProperty("metadata")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PlanMetadata metadata;

    @JsonProperty("free")
    private boolean free = true;

    @JsonProperty("bindable")
    private boolean bindable;


    @JsonProperty("schemas")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Schemas schemas;

}
