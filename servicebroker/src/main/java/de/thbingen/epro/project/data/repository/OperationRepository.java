/*
 * Developed by Jonas Hueg (jhueg) on 20.02.19 19:58.
 * Last modified 20.02.19 19:58.
 * Copyright (c) 2019. All rights reserved.
 */

package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
