/**
 * Defining CatalogService.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.servicebroker.model.Catalog;
import de.thbingen.epro.project.servicebroker.model.ServiceDefinition;

public interface CatalogService {

    Catalog getCatalog();

    Iterable<ServiceDefinition> getServiceDefinitions();

    ServiceDefinition getServiceDefinition(String id);

}
