package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representing Device defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Device implements Serializable {

    @NonNull
    @NotEmpty
    @JsonProperty("volume_id")
    private String volume_id;

    @JsonProperty("mount_config")
    private Map<String, String> mountConfig = new HashMap<>();

}
