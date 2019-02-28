/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import de.thbingen.epro.project.servicebroker.helm.HelmClient;
import de.thbingen.epro.project.servicebroker.helm.Credentials;
import de.thbingen.epro.project.servicebroker.helm.ServiceDetails;
import de.thbingen.epro.project.servicebroker.services.AbstractInstanceBindingService;
import de.thbingen.epro.project.web.exception.ServiceInstanceBindingBadRequestException;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstancebinding.CreateServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.DeleteServiceInstanceBindingResponse;
import de.thbingen.epro.project.web.response.serviceinstancebinding.LastOperationServiceInstanceBindingResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@Slf4j
@RequiredArgsConstructor
public class RedisInstanceBindingService extends AbstractInstanceBindingService {

    @Autowired
    @NonNull
    private final HelmClient helmClient;

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request) {

        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        if (serviceInstance == null)
            throw new ServiceInstanceBindingBadRequestException(bindingId, request);


        if (!(serviceInstance.getPlanId().equals(request.getPlanId())))
            throw new ServiceInstanceBindingBadRequestException(bindingId, request);


        HashMap<String, Object> credentials = new HashMap<>();

        ServiceDetails masterDetails = helmClient.getServiceDetails(instanceId + "-redis-master");
        Credentials secrets = helmClient.getCredentials(instanceId + "-redis");

        String password = secrets.getPassword("redis-password");
        String host = helmClient.getHost();
        String masterPort = masterDetails.getServicePorts().get(0).getNodePort().toString();

        HashMap<String, String> masterCredentials = getCredentialsMap(password, host, masterPort);

        credentials.put("master", masterCredentials);

        if (serviceInstance.getPlanId().equals(RedisService.PLAN_CLUSTER_ID)) {

            ServiceDetails slaveDetails = helmClient.getServiceDetails(instanceId + "-redis-slave");
            String slavePort = slaveDetails.getServicePorts().get(0).getNodePort().toString();

            HashMap<String, String> slaveCredentials = getCredentialsMap(password, host, slavePort);
            credentials.put("slave", slaveCredentials);

        }

        credentials.put("password", password);

        ServiceInstanceBinding serviceInstanceBinding = new ServiceInstanceBinding(serviceInstance);
        serviceInstanceBinding.setCredentials(credentials);
        serviceInstanceBinding.setParameters(request.getParameters());

        serviceInstanceBindingRepository.save(serviceInstanceBinding);

        CreateServiceInstanceBindingResponse response =
                CreateServiceInstanceBindingResponse
                        .builder()
                        .credentials(credentials)
                        .build();

        return response;
    }

    @Override
    public DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request) {

        ServiceInstanceBinding serviceInstanceBinding =
                serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        serviceInstanceBindingRepository.deleteById(bindingId);

        DeleteServiceInstanceBindingResponse response =
                DeleteServiceInstanceBindingResponse
                        .builder()
                        .operation("{}")
                        .build();

        return response;
    }

    @Override
    public LastOperationServiceInstanceBindingResponse lastOperation(String bindingId, String instanceId, LastOperationServiceInstanceBindingRequest request) {
        return null;
    }

    private HashMap<String, String> getCredentialsMap(String password, String host, String port) {

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("uri", String.format("redis://%s@%s", password, host + ":" + port));
        credentials.put("host", host);
        credentials.put("port", port);

        return credentials;
    }

}
