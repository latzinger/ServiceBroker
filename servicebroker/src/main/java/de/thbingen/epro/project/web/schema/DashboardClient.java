package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Representing DashboardClient defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardClient implements Serializable {

    @NonNull
    @NotEmpty
    @JsonProperty("id")
    private String id;

    @NonNull
    @NotEmpty
    @JsonProperty("secret")
    private String secret;

    @NotEmpty
    @JsonProperty("redirect_uri")
    private String redirectUri;

}
