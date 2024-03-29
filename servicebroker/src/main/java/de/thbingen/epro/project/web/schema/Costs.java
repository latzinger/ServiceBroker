package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representing Costs defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@Builder
public class Costs implements Serializable {

    @NonNull
    @NotEmpty
    @JsonProperty("amount")
    private Map<String, String> amount = new HashMap<>();

    @NonNull
    @NotEmpty
    @JsonProperty("unit")
    private String unit;

}
