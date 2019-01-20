package de.thbingen.epro.project.servicebroker.model.services;

import de.thbingen.epro.project.web.services.Service;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceManager {
    private List<? extends Service> definedServices;
    private Map<String, Service> services;

    @Autowired
    public ServiceManager(List<? extends Service> definedServices) {
        this.definedServices = definedServices;
    }

    @PostConstruct
    private void mapServices(){
        services = new HashMap<>();
        definedServices.forEach(service -> services.put(service.getServiceId(), service));
    }

    public List<? extends Service> getDefinedServices() {
        return definedServices;
    }

    public void setDefinedServices(List<? extends Service> definedServices) {
        this.definedServices = definedServices;
        mapServices();
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }

    public Service getService(String serviceId){
        return services.get(serviceId);
    }
}
