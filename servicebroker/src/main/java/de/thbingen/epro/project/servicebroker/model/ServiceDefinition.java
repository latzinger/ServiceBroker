/**
 * Representing a ServiceDefinition Offering Object.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */
/**
 * Representing a Service Definition.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ServiceDefinition implements Serializable {

    @NotEmpty
    @NonNull
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @NonNull
    @JsonProperty("id")
    private String id;

    @NotEmpty
    @NonNull
    @JsonProperty("description")
    private String description;

    @JsonProperty("tags")
    private ArrayList<String> tags = new ArrayList<>();

    @JsonProperty("requires")
    private ArrayList<String> requires = new ArrayList<>();

    @NotEmpty
    @NonNull
    @JsonProperty("bindable")
    private boolean bindable;

    @JsonProperty("instances_retrievable")
    private boolean instancesRetrievable;

    @JsonProperty("bindings_retrievable")
    private boolean bindingsRetrievable;

    @JsonProperty("metadata")
    private Map<String, Object> metadata = new HashMap<>();

    @JsonProperty("dashboard_client")
    private DashboardClient dashboardClient;

    @JsonProperty("plan_updateable")
    private boolean planUpdateable;

    @NotEmpty
    @NonNull
    @JsonProperty("plans")
    private ArrayList<Plan> plans = new ArrayList<>();

}
