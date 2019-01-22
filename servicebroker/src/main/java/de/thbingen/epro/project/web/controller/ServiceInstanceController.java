/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.servicebroker.services.ServiceManager;
import de.thbingen.epro.project.web.request.OsbRequest;
import de.thbingen.epro.project.web.request.serviceinstance.*;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.services.OsbService;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v2/service_instance")
public class ServiceInstanceController extends BaseController {

    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        checkApiVersion(httpHeaders);
        //TODO implement method


        return null;
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody CreateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult, instanceId, parameters);
        //TODO implement method

        ServiceInstanceService serviceInstanceService = getServiceInstanceService(request.getServiceId());
        CreateServiceInstanceResponse createResponse = serviceInstanceService.createServiceInstance(request);


        return null;
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters) {
        DeleteServiceInstanceRequest request = new DeleteServiceInstanceRequest();
        checkAndComplete(httpHeaders, request, null, instanceId, parameters);
        //TODO implement method
        return null;
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody UpdateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult, instanceId, parameters);
        //TODO implement method
        return null;
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instance_id") String instanceId,
            @RequestParam Map<String, String> parameters) {
        LastOperationServiceInstanceRequest request = new LastOperationServiceInstanceRequest();
        checkAndComplete(httpHeaders, request, null, instanceId, parameters);
        //TODO implement method
        return null;
    }

    public void checkAndComplete(HttpHeaders httpHeaders, ServiceInstanceRequest request, BindingResult bindingResult, String instanceId) {
        this.checkAndComplete(httpHeaders, request, bindingResult, instanceId, new HashMap<>());
    }

    public void checkAndComplete(HttpHeaders httpHeaders, ServiceInstanceRequest request, BindingResult bindingResult, String instanceId, Map<String, String> parameters) {
        super.checkAndComplete(httpHeaders, request, bindingResult, parameters);

        request.setInstanceId(instanceId);
    }

    private ServiceInstanceService getServiceInstanceService(String serviceId){
        return getService(serviceId).getServiceInstanceService();
    }
}
