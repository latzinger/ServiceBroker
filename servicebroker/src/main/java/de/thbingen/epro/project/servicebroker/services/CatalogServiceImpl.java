package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.schema.Catalog;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import de.thbingen.epro.project.web.services.CatalogService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Getter
@Setter
@AllArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private Catalog catalog;

    @Override
    public Catalog getCatalog() {
        return catalog;
    }

    @Override
    public ArrayList<ServiceDefinition> getServiceDefinitions() {
        return null;
    }

    @Override
    public ServiceDefinition getServiceDefinition(String id) {
        return null;
    }

}
