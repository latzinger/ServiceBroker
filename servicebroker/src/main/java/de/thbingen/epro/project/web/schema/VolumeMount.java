package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Representing VolumeMount defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@Builder
public class VolumeMount implements Serializable {

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
