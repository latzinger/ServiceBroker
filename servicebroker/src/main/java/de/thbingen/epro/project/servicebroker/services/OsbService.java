package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.schema.ServiceDefinition;

/**
 * Interface for defining an Service Broker Service.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

public interface OsbService {

    /**
     * Gets the {@link ServiceDefinition} of a service
     * @return
     */
    ServiceDefinition getServiceDefiniton();

    /**
     * Gets the id associated with the service
     * @return
     */
    String getServiceId();

    /**
     * Gets the {@link InstanceService} responsible for create, update, delete {@link de.thbingen.epro.project.data.model.ServiceInstance} operations
     * @return
     */
    InstanceService getInstanceService();

    /**
     * Gets the {@link InstanceService} responsible for create, update, delete {@link de.thbingen.epro.project.data.model.ServiceInstanceBinding} operations
     * @return
     */
    BindingService getBindingService();
}
