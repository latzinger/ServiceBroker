/**
 * Representing a Service Plan for a Service Offering Object.
 *
 * @since 1.0
 * @author larsatzinger
 * @version 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
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

}
