
package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.schema.Catalog;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface defining an CatalogService.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

public interface CatalogService {

    Catalog getCatalog();

    List<ServiceDefinition> getServiceDefinitions();

}
