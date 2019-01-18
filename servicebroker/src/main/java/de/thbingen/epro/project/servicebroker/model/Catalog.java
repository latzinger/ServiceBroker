/**
 * Representing a Catalog.
 *
 * @since 1.0
 * @author larsatzinger
 * @version 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Catalog implements Serializable {

    @NonNull
    @JsonProperty("serviceDefinitions")
    private List<ServiceDefinition> serviceDefinitions = new ArrayList<>();

}
