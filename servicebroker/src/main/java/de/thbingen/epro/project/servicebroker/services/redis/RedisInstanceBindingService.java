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

        ServiceDetails masterDetails = helmClient.getServiceDetails(instanceId + "-redis-master");
        ServiceDetails slaveDetails = helmClient.getServiceDetails(instanceId + "-redis-salve");
        Credentials credentials = helmClient.getCredentials(instanceId + "-redis");

        String password = credentials.getPassword("redis-password");
        String host = helmClient.getHost();
        String port = masterDetails.getServicePorts().get(0).getNodePort().toString();

        HashMap<String, String> creds = new HashMap<>();
        creds.put("uri", String.format("redis://%s@%s", password, host));
        creds.put("password", password);
        creds.put("host", host);
        creds.put("port", port);

        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        ServiceInstanceBinding serviceInstanceBinding = new ServiceInstanceBinding(serviceInstance);
        serviceInstanceBinding.setCredentials(creds);
        serviceInstanceBinding.setParameters(request.getParameters());

        serviceInstanceBindingRepository.save(serviceInstanceBinding);

        CreateServiceInstanceBindingResponse response =
                CreateServiceInstanceBindingResponse
                        .builder()
                        .credentials(creds)
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

}
