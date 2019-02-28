package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representing ServiceMetadata defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceMetadata implements Serializable {

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("longDescription")
    private String longDescription;

    @JsonProperty("providerDisplayName")
    private String providerDisplayName;

    @JsonProperty("documentationUrl")
    private String documentationUrl;

    @JsonProperty("supportUrl")
    private String supportUrl;

}
