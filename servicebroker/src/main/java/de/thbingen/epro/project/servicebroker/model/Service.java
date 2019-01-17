/**
 * Representing a Service Offering Object.
 *
 * @since 1.0
 * @author larsatzinger
 * @version 1.0
 */

package de.thbingen.epro.project.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Service implements Serializable {

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
    private Object metadata;

    @JsonProperty("dashboard_client")
    private DashboardClient dashboardClient;

    @JsonProperty("plan_updateable")
    private boolean planUpdateable;

    @NotEmpty
    @NonNull
    @JsonProperty("plans")
    private ArrayList<Plan> plans = new ArrayList<>();

}