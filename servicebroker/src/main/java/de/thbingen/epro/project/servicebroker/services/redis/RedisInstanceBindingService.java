package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.servicebroker.helm.HelmClient;
import de.thbingen.epro.project.servicebroker.helm.ServiceDetails;
import de.thbingen.epro.project.servicebroker.services.AbstractInstanceBindingService;
import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Redis-BindingService for handling binding requests.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisInstanceBindingService extends AbstractInstanceBindingService {

    @Autowired
    @NonNull
    private final HelmClient helmClient;

    /**
     * @param bindingId  binding_id for the {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param request    {@link CreateServiceInstanceBindingRequest} containing necessary information
     * @return {@link CreateServiceInstanceBindingResponse}  or throwing an Exception
     */
    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request) {

        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        if (serviceInstance == null)
            throw new InvalidRequestException();


        if (!(serviceInstance.getPlanId().equals(request.getPlanId())))
            throw new InvalidRequestException();

        ServiceInstanceBinding serviceInstanceBinding = new ServiceInstanceBinding(bindingId, serviceInstance);
        serviceInstanceBindingRepository.save(serviceInstanceBinding);

        log.debug("ServiceInstanceBinding with binding_id: " + bindingId + " and instance_id: " + instanceId + " saved");

        Operation operation = createOperation(serviceInstance, serviceInstanceBinding);

        helmClient.getCredentialsAsync(instanceId + "-redis", secrets -> {
            helmClient.waitForInstanceReady(instanceId);

            ServiceDetails masterDetails = helmClient.getServiceDetails(instanceId + "-redis-master");
            HashMap<String, String> credentials = new HashMap<>();

            String password = secrets.getPassword("redis-password");
            String host = helmClient.getHost();
            String masterPort = masterDetails.getServicePorts().get(0).getNodePort().toString();

            credentials.put("password", password);

            credentials.put("master-uri", String.format("redis://%s@%s", password, host + ":" + masterPort));
            credentials.put("master-host", host);
            credentials.put("master-port", masterPort);

            if (serviceInstance.getPlanId().equals(RedisService.PLAN_CLUSTER_ID)) {

                ServiceDetails slaveDetails = helmClient.getServiceDetails(instanceId + "-redis-slave");
                String slavePort = slaveDetails.getServicePorts().get(0).getNodePort().toString();

                credentials.put("slave-uri", String.format("redis://%s@%s", password, host + ":" + slavePort));
                credentials.put("slave-host", host);
                credentials.put("slave-port", slavePort);
            }


            serviceInstanceBinding.setCredentials(credentials);
            serviceInstanceBinding.setParameters(request.getParameters());

            log.debug("set Credentials for binding_id: " + bindingId);
            log.debug("set Parameters for binding_id: " + bindingId);

            serviceInstanceBindingRepository.save(serviceInstanceBinding);
        }, operation);

        CreateServiceInstanceBindingResponse response =
                CreateServiceInstanceBindingResponse
                        .builder()
                        .operation("" + operation.getId())
                        .build();

        return response;
    }

    /**
     * @param bindingId  binding_id of an existing {@link ServiceInstanceBinding}
     * @param instanceId instance_id of an existing {@link ServiceInstance}
     * @param request    {@link DeleteServiceInstanceBindingRequest} containing necessary information
     * @return {@link DeleteServiceInstanceBindingResponse} or throwing an Exception
     */
    @Override
    public DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request) {

        ServiceInstanceBinding serviceInstanceBinding = serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        if (!(serviceInstanceBinding.getServiceInstance().getServiceId().equals(request.getRequestParameters().get("service_id")) ||
                serviceInstanceBinding.getServiceInstance().getPlanId().equals(request.getRequestParameters().get("plan_id"))))
            throw new InvalidRequestException();

        serviceInstanceBindingRepository.deleteById(bindingId);

        log.debug("deleted ServiceInstanceBinding with binding_id: " + bindingId + " used by ServiceInstance with instance_id: " + instanceId);

        return new DeleteServiceInstanceBindingResponse();
    }

}
