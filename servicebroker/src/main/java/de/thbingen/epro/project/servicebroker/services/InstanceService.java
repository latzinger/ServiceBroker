
package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.exception.ServiceInstanceAlreadyExistsException;
import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.LastOperationServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.LastOperationServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.UpdateServiceInstanceResponse;

/**
 * TODO add description
 * Interface for classes that provide functionality to create, update, delete {@link de.thbingen.epro.project.data.model.ServiceInstance} for concrete {@link OsbService}
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

public interface InstanceService {

    /**
     * Creates an {@link de.thbingen.epro.project.data.model.ServiceInstance} for {@link OsbService}
     * @param request
     * @return
     * @throws ServiceInstanceAlreadyExistsException
     */
    CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) throws ServiceInstanceAlreadyExistsException;

    /**
     * Updates an {@link de.thbingen.epro.project.data.model.ServiceInstance} for {@link OsbService}
     * @param request
     * @return
     */
    UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request);

    /**
     * Delete an {@link de.thbingen.epro.project.data.model.ServiceInstance} of {@link OsbService}
     * @param request
     * @return
     */
    DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request);

    /**
     * Get status of last asynchronous create, update or delete operation
     * @param request
     * @return
     */
    LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request);

}
