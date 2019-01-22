/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
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
    private PlanMetadata metadata;

    @JsonProperty("free")
    private boolean free = true;

    @JsonProperty("bindable")
    private boolean bindable;

    @JsonProperty("plan_updateable")
    private boolean planUpdateable;

    @JsonProperty("schemas")
    private Schemas schemas;

}
