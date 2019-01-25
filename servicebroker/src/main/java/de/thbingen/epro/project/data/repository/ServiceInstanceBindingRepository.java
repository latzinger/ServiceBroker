/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.repository;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInstanceBindingRepository extends JpaRepository<ServiceInstanceBinding, String> {

}
