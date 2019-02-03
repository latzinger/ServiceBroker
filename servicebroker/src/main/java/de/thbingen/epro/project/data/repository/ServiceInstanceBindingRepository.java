/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceInstanceBindingRepository extends JpaRepository<ServiceInstanceBinding, String> {

    @Query("FROM ServiceInstanceBinding s WHERE s.id = :bindingId and s.serviceInstance.id = :instanceId")
    ServiceInstanceBinding getServiceInstanceBinding(@Param("instanceId") String instanceId,
                                                     @Param("bindingId") String bindingId);

}
