/**
 * TODO add description
 *
 * @author jonashueg
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServiceInstanceService {
    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    public ServiceInstance getServiceInstance(String instanceId) throws ServiceInstanceNotFoundException {
        ServiceInstance serviceInstance = serviceInstanceRepository.getOne(instanceId);
        if (serviceInstance == null)
            throw new ServiceInstanceNotFoundException(instanceId);
        return serviceInstance;
    }

}
