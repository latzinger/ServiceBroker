/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.servicebroker.services.AbstractInstanceBindingService;
import de.thbingen.epro.project.web.exception.*;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class RedisInstanceBindingService extends AbstractInstanceBindingService {

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request)
            throws ServiceInstanceBindingBadRequestException {

        ServiceInstanceBinding serviceInstanceBinding =
                serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        if (serviceInstanceBinding != null) {

            Map<String, String> parameters = serviceInstanceBinding.getParameters();
            Map<String, String> requestedParam = request.getRequestParameters();

            if (parameters.equals(requestedParam))
                throw new ServiceInstanceBindingAlreadyExistsException(serviceInstanceBinding, true);
            else
                throw new ServiceInstanceBindingAlreadyExistsException(serviceInstanceBinding, false);

        }

//        if (request.getServiceId() != redisService.getServiceId())
//            throw new ServiceInstanceBindingBadRequestException(bindingId, request.toString());

        CreateServiceInstanceBindingResponse response =
                CreateServiceInstanceBindingResponse.builder()
                        .credentials(serviceInstanceBinding.getCredentials())
                        .build();

        return response;
    }

    @Override
    public DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request) {
        return null;
    }

    @Override
    public LastOperationServiceInstanceBindingResponse lastOperation(String bindingId, String instanceId, LastOperationServiceInstanceBindingRequest request) {
        return null;
    }

}
