/**
 * Representing a device.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Device implements Serializable {

    @NonNull
    @NotEmpty
    @JsonProperty("volume_id")
    private String volume_id;

    @JsonProperty("mount_config")
    private Map<String, String> mountConfig = new HashMap<>();

}
