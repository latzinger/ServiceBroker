package de.thbingen.epro.project.web.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Map;

@Data
public class OsbRequest {
    @JsonIgnore
    private Map<String, String> httpHeaders;
}
