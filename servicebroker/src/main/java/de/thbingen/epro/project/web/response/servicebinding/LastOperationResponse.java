package de.thbingen.epro.project.web.response.servicebinding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LastOperationResponse {

    @NonNull
    @NotEmpty
    @JsonProperty("state")
    private String state;

    @JsonProperty("description")
    private String description;

}
