package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.schema.ServiceDefinition;

/**
 * Interface defining an Service Broker Service.
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

public interface OsbService {

    ServiceDefinition getServiceDefiniton();

    String getServiceId();

    InstanceService getInstanceService();

    BindingService getBindingService();
}
