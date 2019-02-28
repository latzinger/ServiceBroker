package de.thbingen.epro.project.servicebroker.services;

import de.thbingen.epro.project.web.exception.ServiceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handling and managing all Service Broker Services ({@link OsbService}) found.
 * Detecting services via Spring component scan.
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Service
@Slf4j
public class ServiceManager {

    private List<? extends OsbService> definedServices;

    private Map<String, OsbService> services;

    @Autowired
    public ServiceManager(List<? extends OsbService> definedServices) {
        this.definedServices = definedServices;
    }

    @PostConstruct
    private void mapServices() {
        services = new HashMap<>();

        long count = definedServices.stream()
                .map(o -> o.getServiceId())
                .distinct()
                .count();

        if (definedServices.size() > count)
            throw new IllegalStateException("There are " + (definedServices.size() - count) + " Services with same serviceId");

        definedServices.forEach(service -> services.put(service.getServiceId(), service));
    }

    /**
     * Returns a list of defined {@link OsbService}'s scanned with Spring package scan
     * @return
     */
    public List<? extends OsbService> getDefinedServices() {
        return definedServices;
    }

    public void setDefinedServices(List<? extends OsbService> definedServices) {
        this.definedServices = definedServices;
        mapServices();
    }

    public Map<String, OsbService> getServices() {
        return services;
    }

    public void setServices(Map<String, OsbService> services) {
        this.services = services;
    }

    public OsbService getService(String serviceId) throws ServiceNotFoundException {

        OsbService service = services.get(serviceId);

        if (service == null)
            throw new ServiceNotFoundException(serviceId);

        return service;
    }
}
