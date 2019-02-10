/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column
    private String dashboardURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SERVICE_INSTANCE_PARAMETERS")
    @MapKeyJoinColumn(name = "PARAMETER_KEY")
    @Column(name = "VALUE")
    private Map<String, String> parameters;

    public String getParamater(Object key) {
        return parameters.get(key);
    }

    public String putParameter(String key, String value) {
        return parameters.put(key, value);
    }

    public String removeParameter(Object key) {
        return parameters.remove(key);
    }
}
