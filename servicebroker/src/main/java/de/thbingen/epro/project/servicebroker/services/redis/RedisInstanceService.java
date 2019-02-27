package de.thbingen.epro.project.servicebroker.services.redis;

import de.thbingen.epro.project.data.model.Operation;
import de.thbingen.epro.project.data.model.ServiceInstance;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import de.thbingen.epro.project.servicebroker.helm.*;
import de.thbingen.epro.project.servicebroker.helm.exceptions.InstallFailedException;
import de.thbingen.epro.project.servicebroker.services.AbstractInstanceService;
import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.exception.ServiceInstanceAlreadyExistsException;
import de.thbingen.epro.project.web.exception.ServiceInstanceNotFoundException;
import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.LastOperationServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.DeleteServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.LastOperationServiceInstanceResponse;
import de.thbingen.epro.project.web.response.serviceinstance.UpdateServiceInstanceResponse;
import de.thbingen.epro.project.web.schema.Plan;
import de.thbingen.epro.project.web.schema.ServiceDefinition;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor()
public class RedisInstanceService extends AbstractInstanceService {
    private static final String chartUrlString = "https://kubernetes-charts.storage.googleapis.com/redis-5.3.0.tgz";
    private URL chartUrl;
    private ChartBuilder chartBuilder;
    private ChartConfig defaultConfig;

    @Autowired
    @Lazy
    private RedisService redisService;

    @NonNull
    private final HelmClient helmClient;


    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) throws ServiceInstanceAlreadyExistsException {
        log.debug("Create ServiceInstance");
        checkAcceptIncomplete(request);
        String planId = request.getPlanId();


        ServiceDefinition serviceDefiniton = redisService.getServiceDefiniton();
        Plan plan = serviceDefiniton.getPlan(planId);

        if (plan == null) {
            throw new InvalidRequestException("Plan " + planId + " for serviceId " + request.getServiceId() + " not found");
        }

        ServiceInstance serviceInstance = createServiceInstanceEntry(request);
        Operation operation = createOperation(serviceInstance);

        log.debug("Created serviceInstance and operation, go on with plan " + planId);

        switch (planId) {
            case RedisService.PLAN_SMALL_ID:
                createSmallPlanServiceInstance(request, operation);
                break;
            case RedisService.PLAN_STANDARD_ID:
                createStandardPlanServiceInstance(request, operation);
                break;
            case RedisService.PLAN_CLUSTER_ID:
                createClusterPlanServiceInstance(request, operation);
                break;
        }
        CreateServiceInstanceResponse createServiceInstanceResponse = new CreateServiceInstanceResponse(null, "" + operation.getId());
        return createServiceInstanceResponse;
    }

    private void createSmallPlanServiceInstance(CreateServiceInstanceRequest request, @NotNull Operation operation) {
        log.debug("Create Redis plan: small");
        ChartConfig chartConfig = new ChartConfig();
        chartConfig.mergeFrom(defaultConfig);

        //using default settings

        chartBuilder.setChartConfig(chartConfig);
        helmClient.installChartAsync(chartBuilder, request.getInstanceId(), 300, operation);
    }

    private void createStandardPlanServiceInstance(CreateServiceInstanceRequest request, @NotNull Operation operation) {
        log.debug("Create Redis plan: standard");
        ChartConfig chartConfig = new ChartConfig();
        chartConfig.mergeFrom(defaultConfig);

        chartConfig.set("master.resources.memory", "1024Mi");
        chartConfig.set("master.resources.cpu", "400m");

        chartBuilder.setChartConfig(chartConfig);
        helmClient.installChartAsync(chartBuilder, request.getInstanceId(), 300, operation);
    }

    private void createClusterPlanServiceInstance(CreateServiceInstanceRequest request, @NotNull Operation operation) {
        log.debug("Create Redis plan: cluster");
        ChartConfig chartConfig = new ChartConfig();
        chartConfig.mergeFrom(defaultConfig);

        chartConfig.set("cluster.enabled", "true");
        chartConfig.set("cluster.slaveCount", 3);
        chartConfig.set("master.resources.memory", "1024Mi");
        chartConfig.set("master.resources.cpu", "400m");

        chartBuilder.setChartConfig(chartConfig);
        helmClient.installChartAsync(chartBuilder, request.getInstanceId(), 500, operation);
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
        return null;
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        checkAcceptIncomplete(request);
        checkSerivceIdAndPlanId(request);
        ServiceInstance serviceInstance = getServiceInstance(request.getInstanceId());
        Operation operation = createOperation(serviceInstance);


        helmClient.uninstallChartAsync(request.getInstanceId(), 500, operation);
        DeleteServiceInstanceResponse deleteServiceInstanceResponse = new DeleteServiceInstanceResponse("" + operation.getId());
        return deleteServiceInstanceResponse;
    }

    @Override
    public LastOperationServiceInstanceResponse lastOperation(LastOperationServiceInstanceRequest request) {
        log.debug("Get lastOpartion for " + request.getInstanceId() + " with operationId " + request.getOperationId());
        Long operationId = getOperationId(request);

        Operation operation = operationRepository.getOperation(request.getInstanceId(), operationId);

        LastOperationServiceInstanceResponse response = new LastOperationServiceInstanceResponse("unknown", "Something went wrong");
        if (operation != null)
            response = LastOperationServiceInstanceResponse.builder()
                    .state(operation.getState().toString())
                    .description(operation.getMessage())
                    .build();

        return response;
    }

    private Long getOperationId(LastOperationServiceInstanceRequest request) {
        String operationIdString = request.getOperationId();
        try {
            return Long.parseLong(operationIdString);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    @PostConstruct
    private void postConstruct() throws IOException {
        chartUrl = URI.create(chartUrlString).toURL();
        chartBuilder = helmClient.loadChart(chartUrl);
        defaultConfig = chartBuilder.getChartConfig();

        defaultConfig.set("master.service.type", "NodePort");
        defaultConfig.set("slave.service.type", "NodePort");

        defaultConfig.set("cluster.enabled", "false");
        defaultConfig.set("master.resources.memory", "256Mi");
        defaultConfig.set("master.resources.cpu", "100m");
    }
}
