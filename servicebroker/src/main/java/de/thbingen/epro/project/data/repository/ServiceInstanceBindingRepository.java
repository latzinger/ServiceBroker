package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInstanceBindingRepository extends JpaRepository<ServiceInstanceBinding, String> {

}
