package de.thbingen.epro.project.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Class for asynchronous operations for create and delete {@link ServiceInstance} and {@link ServiceInstanceBinding}
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "operation_seq", allocationSize = 10)
public class Operation extends AbstractLongId{

    @ManyToOne
    @JoinColumn(name="SERVICE_INSTANCE_ID")
    @NonNull
    @ToString.Exclude
    @NotNull
    @JsonIgnore
    private ServiceInstance serviceInstance;


    /**
     * Only non null if {@link Operation} is binding related
     */
    @ManyToOne
    @JoinColumn(name = "SERVICE_INSTANCE_BINDING_ID")
    @ToString.Exclude
    @JsonIgnore
    private ServiceInstanceBinding serviceInstanceBinding;

    @Column
    @Enumerated(EnumType.STRING)
    @NonNull
    @NotNull
    private OperationState state;

    @Column
    @NonNull
    @NotNull
    private String message;

    /**
     * Enum for {@link Operation} state
     */
    public static enum OperationState{
        IN_PROGRESS("in progress"),
        SUCCEEDED("succeeded"),
        FAILED("failed");

        private String text;

        OperationState(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
