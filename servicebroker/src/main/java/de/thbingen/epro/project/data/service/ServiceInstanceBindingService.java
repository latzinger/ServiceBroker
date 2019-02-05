/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.data.repository.ServiceInstanceBindingRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class ServiceInstanceBindingService {

    @Autowired
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    public ServiceInstanceBinding getServiceInstanceBinding(String instanceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException {

        ServiceInstanceBinding serviceInstanceBinding =
                serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        if (serviceInstanceBinding == null)
            throw new ServiceInstanceBindingNotFoundException(bindingId);

        return serviceInstanceBinding;
    }

    public boolean serviceInstanceBindingExists(String bindingId, String instanceId) {
        return serviceInstanceBindingRepository.existsByIdAndServiceInstance_Id(bindingId, instanceId);
    }

    public ServiceInstanceBinding createServiceInstanceBinding(String serviceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException {


        return null;
    }

    public void deleteServiceInstanceBinding(String serviceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException {


        return;
    }

    public void lastOperationServiceInstanceBinding(String serviceId, String bindingId)
            throws ServiceInstanceBindingNotFoundException {


        return;
    }


}
