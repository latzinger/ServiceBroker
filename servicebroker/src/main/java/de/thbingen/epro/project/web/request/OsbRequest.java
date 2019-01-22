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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class OsbRequest {

    @NonNull
    @JsonIgnore
    private Map<String, String> httpHeaders = new HashMap<>();

    @JsonIgnore
    private Map<String, String> parameters = new HashMap<>();

    public OsbRequest(@NonNull Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }
}
