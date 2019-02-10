/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.response.serviceinstance.*;

public interface InstanceService {

    CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request);

    UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request);

    DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request);

    LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request);

}
