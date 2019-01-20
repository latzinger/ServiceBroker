/**
 * Representing a Volume Mount.
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

@Data
@AllArgsConstructor
public class VolumeMount implements Serializable{

    @NonNull
    @NotEmpty
    @JsonProperty("driver")
    private String driver;

    @NonNull
    @NotEmpty
    @JsonProperty("container_dir")
    private String containerDir;

    @NonNull
    @NotEmpty
    @JsonProperty("mode")
    private String mode;

    @NonNull
    @NotEmpty
    @JsonProperty("device_type")
    private String deviceType;

    @NonNull
    @NotEmpty
    @JsonProperty("device")
    private Device device;

}
