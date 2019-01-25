/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.schema.Catalog;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface CatalogService {

    Catalog getCatalog();

    List<ServiceDefinition> getServiceDefinitions();

}
