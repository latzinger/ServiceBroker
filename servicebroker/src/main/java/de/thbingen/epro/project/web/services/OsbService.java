/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.web.schema.ServiceDefinition;

public interface OsbService {

    ServiceDefinition getServiceDefiniton();

    ServiceInstanceService getServiceInstanceService();

    String getServiceId();

}
