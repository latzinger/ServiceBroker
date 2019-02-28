package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * {@link org.springframework.data.repository.Repository} for {@link Operation}s
 */

public interface OperationRepository extends JpaRepository<Operation, Long> {

    /**
     * Get {@link Operation} matching instanceId and operationId
     * @param instanceId
     * @param operationId
     * @return
     */
    @Query("from Operation o where o.serviceInstance.id = :instanceId and o.id = :operationId")
    Operation getOperation(@Param("instanceId") String instanceId, @Param("operationId") Long operationId);


    /**
     * Get {@link Operation} matching instanceId, bindingId and operationId
     * @param instanceId
     * @param bindingId
     * @param operationId
     * @return
     */
    @Query("from Operation o where o.serviceInstance.id = :instanceId and o.id = :operationId and o.serviceInstanceBinding.id = :bindingId")
    Operation getBindingOperation(@Param("instanceId") String instanceId, @Param("bindingId") String bindingId, @Param("operationId") Long operationId);
}
