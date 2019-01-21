/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Map;

@Data
public abstract class OsbRequest {
    @JsonIgnore
    private Map<String, String> httpHeaders;

    @JsonIgnore
    private Map<String, String> parameters;
}
