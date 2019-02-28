/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ServiceInstance extends AbstractEntity {

    @Column(nullable = false)
    @NotNull
    @NonNull
    private String serviceId;

    @Column(nullable = false)
    @NotNull
    @NonNull
    private String planId;

    @Column(nullable = false)
    private boolean initialized;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dashboardURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SERVICE_INSTANCE_PARAMETERS")
    @MapKeyJoinColumn(name = "PARAMETER_KEY")
    @Column(name = "VALUE")
    private Map<String, String> parameters;

    @OneToMany(mappedBy = "serviceInstance", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Operation> operations;

    @OneToMany(mappedBy = "serviceInstance", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ServiceInstanceBinding> bindings;

    public void addOperation(Operation operation){
        operations.add(operation);
    }

    public String getParamater(String key) {
        return parameters.get(key);
    }

    public String putParameter(String key, String value) {
        return parameters.put(key, value);
    }

    public String removeParameter(String key) {
        return parameters.remove(key);
    }

    @Override
    @JsonIgnore
    public @NonNull String getId() {
        return super.getId();
    }

    public ServiceInstance(String instanceId, String serviceId, String planId) {
        super(instanceId);
        this.serviceId = serviceId;
        this.planId = planId;
    }
}
