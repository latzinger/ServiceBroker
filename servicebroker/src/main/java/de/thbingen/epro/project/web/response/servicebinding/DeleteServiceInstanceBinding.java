package de.thbingen.epro.project.web.response.servicebinding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteServiceInstanceBinding {

    @JsonProperty("operation")
    private String operation;

}
