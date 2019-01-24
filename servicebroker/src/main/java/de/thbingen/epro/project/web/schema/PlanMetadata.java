/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */
package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanMetadata implements Serializable {

    @JsonProperty("bullets")
    private List<String> bullets = new ArrayList<>();

    @JsonProperty("costs")
    private List<Costs> costs = new ArrayList<>();

    @JsonProperty("string")
    private String string;

}
