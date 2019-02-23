/*
 * Developed by Jonas Hueg (jhueg) on 20.02.19 19:58.
 * Last modified 20.02.19 19:58.
 * Copyright (c) 2019. All rights reserved.
 */

package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("from Operation o where o.serviceInstance.id = :instanceId and o.id = :operationId")
    Operation getOperation(@Param("instanceId") String instanceId, @Param("operationId") Long operationId);
}
