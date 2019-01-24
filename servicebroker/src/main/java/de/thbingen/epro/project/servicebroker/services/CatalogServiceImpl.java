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
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private ServiceManager serviceManager;

    private Catalog catalog;

    @Override
    public Catalog getCatalog() {

        List<ServiceDefinition> serviceDefinitions = serviceManager.getDefinedServices()
                .stream()
                .map(o -> ((OsbService) o).getServiceDefiniton())
                .collect(Collectors.toList());

        return (catalog = new Catalog(serviceDefinitions));
    }

    @Override
    public List<ServiceDefinition> getServiceDefinitions() {
        return catalog.getServiceDefinitions();
    }

}
