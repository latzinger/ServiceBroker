package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representing Schemas defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schemas implements Serializable {

    @JsonProperty("service_instance")
    private ServiceInstanceSchema serviceInstance;

    @JsonProperty("service_binding")
    private ServiceBindingSchema serviceBinding;

}
