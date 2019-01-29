/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.data.repository.ServiceInstanceBindingRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
public class ServiceInstanceBindingService {

    @Autowired
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    public ServiceInstanceBinding getServiceInstanceBinding(String bindingId, String instanceId)
            throws ServiceInstanceBindingNotFoundException {

        ServiceInstanceBinding serviceInstanceBinding = serviceInstanceBindingRepository.getOne(bindingId);
        ServiceInstance serviceInstance = serviceInstanceRepository.getOne(instanceId);

        if(serviceInstanceBinding == null)
            throw new ServiceInstanceBindingNotFoundException(bindingId);

        boolean sameServiceInstance = serviceInstanceBinding.getServiceInstance().equals(serviceInstance);

        if(serviceInstance == null || !sameServiceInstance)
            throw new ServiceInstanceBindingNotFoundException(bindingId);

        return serviceInstanceBinding;
    }

    public ServiceInstanceBinding createServiceInstanceBinding(String serviceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException {


        return null;
    }

    public void deleteServiceInstanceBinding(String serviceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException {


        return;
    }

    public void lastOperationServiceInstanceBinding(String serviceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException, ServiceInstanceNotFoundException {


        return;
    }


}
