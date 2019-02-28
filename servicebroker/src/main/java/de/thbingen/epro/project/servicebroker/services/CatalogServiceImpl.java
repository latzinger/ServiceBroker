package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.schema.Catalog;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of CatalogService.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Service
@Getter
@Setter
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private ServiceManager serviceManager;

    private Catalog catalog;

    /**
     * @return Catalog containing List with all found {@link ServiceDefinition}
     */
    @Override
    public Catalog getCatalog() {

        List<ServiceDefinition> serviceDefinitions = serviceManager.getDefinedServices()
                .stream()
                .map(o -> ((OsbService) o).getServiceDefiniton())
                .collect(Collectors.toList());

        return (catalog = new Catalog(serviceDefinitions));
    }

    /**
     * @return List containing all {@link ServiceDefinition}
     */
    @Override
    public List<ServiceDefinition> getServiceDefinitions() {
        return catalog.getServiceDefinitions();
    }

}
