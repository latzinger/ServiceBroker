package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, String> {

}
