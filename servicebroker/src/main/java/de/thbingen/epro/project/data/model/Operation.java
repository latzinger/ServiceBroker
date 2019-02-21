/*
 * Developed by Jonas Hueg (jhueg) on 20.02.19 16:59.
 * Last modified 20.02.19 16:59.
 * Copyright (c) 2019. All rights reserved.
 */

package de.thbingen.epro.project.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "operation_seq")
public class Operation extends AbstractLongId{

    @ManyToOne
    @JoinColumn(name="SERVICE_INSTANCE_ID")
    @NonNull
    @NotNull
    private ServiceInstance serviceInstance;

    @Column
    @Enumerated(EnumType.STRING)
    @NonNull
    @NotNull
    private OperationState state;

    @Column
    @NonNull
    @NotNull
    private String message;

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
