package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representing Catalog defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Catalog implements Serializable {

    @JsonProperty("services")
    private List<ServiceDefinition> serviceDefinitions = new ArrayList<>();

}
