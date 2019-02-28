package de.thbingen.epro.project.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity representing an ServiceInstanceBinding.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ServiceInstanceBinding extends AbstractEntity {

    @NonNull
    @ManyToOne
    @JsonIgnore
    private ServiceInstance serviceInstance;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SERVICE_INSTANCE_BINDING_CREDENTIALS")
    @MapKeyJoinColumn(name = "CREDENTIALS_KEY")
    @Column(name = "CREDENTIALS_VALUE")
    private Map<String, String> credentials = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SERVICE_INSTANCE_BINDING_PARAMETERS")
    @MapKeyJoinColumn(name = "PARAMETERS_KEY")
    @Column(name = "PARAMETERS_VALUE")
    private Map<String, String> parameters;


    @OneToMany(mappedBy = "serviceInstanceBinding", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Operation> operations;


    public ServiceInstanceBinding(@NonNull String id, @NonNull ServiceInstance serviceInstance) {
        super(id);
        this.serviceInstance = serviceInstance;
    }

    @JsonIgnore
    @Override
    public @NonNull String getId() {
        return super.getId();
    }
}
