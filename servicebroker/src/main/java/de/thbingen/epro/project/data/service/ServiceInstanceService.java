/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceInstanceService {
    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    public ServiceInstance getServiceInstance(String id){
        ServiceInstance serviceInstance = serviceInstanceRepository.getOne(id);
        return serviceInstance;
    }
}
