/**
 * Representing a Catalog.
 *
 * @since 1.0
 * @author larsatzinger
 * @version 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Catalog implements Serializable {

    @NonNull
    @JsonProperty("services")
    private List<Service> services = new ArrayList<>();

}
