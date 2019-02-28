package de.thbingen.epro.project.web.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Defining default model of an Service Broker Request.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
@NoArgsConstructor
public abstract class OsbRequest {

    @NonNull
    @JsonIgnore
    private Map<String, String> httpHeaders = new HashMap<>();

    @JsonIgnore
    private Map<String, String> requestParameters = new HashMap<>();

    public OsbRequest(@NonNull Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }
}
