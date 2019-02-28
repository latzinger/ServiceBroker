package de.thbingen.epro.project.web.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representing ServiceDefinition defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDefinition implements Serializable {

    public enum Permissions {
        SYSLOG_DRAIN("syslog_drain"),
        ROUTE_FORWARDING("route_forwarding"),
        VOLUME_MOUNT("volume_mount");

        private final String permission;

        Permissions(String permission) {
            this.permission = permission;
        }

        @JsonValue
        public String getPermission() {
            return permission;
        }
    }

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<String> tags = new ArrayList<>();

    @JsonProperty("requires")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Permissions> requires = new ArrayList<>();

    @NotEmpty
    @NonNull
    @JsonProperty("bindable")
    private boolean bindable;

    @JsonProperty("instances_retrievable")
    private boolean instancesRetrievable = true;

    @JsonProperty("bindings_retrievable")
    private boolean bindingsRetrievable = true;

    @JsonProperty("metadata")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceMetadata metadata;

    @JsonProperty("dashboard_client")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DashboardClient dashboardClient;

    @JsonProperty("plan_updateable")
    private boolean planUpdateable;

    @NotEmpty
    @NonNull
    @JsonProperty("plans")
    private ArrayList<Plan> plans = new ArrayList<>();

    public Plan getPlan(String planId){
        List<Plan> collect = plans.stream()
                .filter(plan -> plan.getId().equals(planId))
                .collect(Collectors.toList());

        return collect.size() > 0 ? collect.get(0) : null;
    }
}
