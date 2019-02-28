package de.thbingen.epro.project.servicebroker.services.postgres;

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
 * Implementation of {@link de.thbingen.epro.project.servicebroker.services.BindingService} for postgres
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostgresInstanceBindingService extends AbstractInstanceBindingService {

    @Autowired
    @NonNull
    private final HelmClient helmClient;

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(String bindingId, String instanceId, CreateServiceInstanceBindingRequest request) {

        ServiceInstance serviceInstance = serviceInstanceRepository.getServiceInstanceById(instanceId);

        if (serviceInstance == null)
            throw new InvalidRequestException();


        if (!(serviceInstance.getPlanId().equals(request.getPlanId())))
            throw new InvalidRequestException();

        ServiceInstanceBinding serviceInstanceBinding = new ServiceInstanceBinding(bindingId, serviceInstance);
        serviceInstanceBindingRepository.save(serviceInstanceBinding);

        Operation operation = createOperation(serviceInstance, serviceInstanceBinding);

        helmClient.getCredentialsAsync(instanceId + "-postgresql", secrets -> {
            helmClient.waitForInstanceReady(instanceId);

            ServiceDetails masterDetails = helmClient.getServiceDetails(instanceId + "-postgresql");
            HashMap<String, String> credentials = new HashMap<>();

            String password = secrets.getPassword("postgresql-password");
            String host = helmClient.getHost();
            String masterPort = masterDetails.getServicePorts().get(0).getNodePort().toString();

            credentials.put("password", password);

            credentials.put("uri", String.format("postgresql://%s@%s", "postgres:" + password, host + ":" + masterPort));
            credentials.put("host", host);
            credentials.put("port", masterPort);
            credentials.put("user", "postgres");

            if (serviceInstance.getPlanId().equals(PostgresService.PLAN_CLUSTER_ID)) {

                ServiceDetails slaveDetails = helmClient.getServiceDetails(instanceId + "-postgresql-read");
                String slavePort = slaveDetails.getServicePorts().get(0).getNodePort().toString();

                credentials.put("read-uri", String.format("postgresql://%s@%s", password, host + ":" + slavePort));
                credentials.put("read-host", host);
                credentials.put("read-port", slavePort);
            }


            serviceInstanceBinding.setCredentials(credentials);
            serviceInstanceBinding.setParameters(request.getParameters());

            serviceInstanceBindingRepository.save(serviceInstanceBinding);
        }, operation);

        CreateServiceInstanceBindingResponse response =
                CreateServiceInstanceBindingResponse
                        .builder()
                        .operation("" + operation.getId())
                        .build();

        return response;
    }

    @Override
    public DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(String bindingId, String instanceId, DeleteServiceInstanceBindingRequest request) {

        ServiceInstanceBinding serviceInstanceBinding = serviceInstanceBindingRepository.getServiceInstanceBinding(instanceId, bindingId);

        if (!(serviceInstanceBinding.getServiceInstance().getServiceId().equals(request.getRequestParameters().get("service_id")) ||
                serviceInstanceBinding.getServiceInstance().getPlanId().equals(request.getRequestParameters().get("plan_id"))))
            throw new InvalidRequestException();

        serviceInstanceBindingRepository.deleteById(bindingId);

        return new DeleteServiceInstanceBindingResponse();
    }

}
