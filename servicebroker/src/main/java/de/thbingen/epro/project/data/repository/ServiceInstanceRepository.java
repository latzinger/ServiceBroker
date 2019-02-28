package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */
public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, String> {

    /**
     * Get {@link ServiceInstance} by instanceId
     * @param instanceId
     * @return
     */
    ServiceInstance getServiceInstanceById(String instanceId);

}
