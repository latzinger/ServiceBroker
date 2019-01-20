/**
 * Contains the data necessary to activate the Dashboard SSO feature for specific ServiceDefinition.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
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
