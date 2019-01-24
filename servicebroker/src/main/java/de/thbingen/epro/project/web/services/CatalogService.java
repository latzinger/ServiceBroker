/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.services;

import de.thbingen.epro.project.web.schema.Catalog;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface CatalogService {

    Catalog getCatalog();

    ArrayList<ServiceDefinition> getServiceDefinitions();

    ServiceDefinition getServiceDefinition(String id);

}
