package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.servicebroker.model.ServiceDefinition;

public interface Service {
    ServiceDefinition getServiceDefiniton();
    ServiceInstanceService getServiceInstanceService();

    String getServiceId();
}
