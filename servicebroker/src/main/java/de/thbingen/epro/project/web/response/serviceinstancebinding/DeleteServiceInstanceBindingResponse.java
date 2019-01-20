package de.thbingen.epro.project.web.response.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteServiceInstanceBindingResponse {

    @JsonProperty("operation")
    private String operation;

}
