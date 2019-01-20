/**
 * Representing schemas.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schemas implements Serializable {

    @JsonProperty("service_instance")
    private ServiceInstanceSchema serviceInstance;

    @JsonProperty("service_binding")
    private ServiceBindingSchema serviceBinding;

}
