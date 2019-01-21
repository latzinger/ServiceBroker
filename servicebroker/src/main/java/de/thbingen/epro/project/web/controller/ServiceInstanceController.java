/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import de.thbingen.epro.project.web.services.ServiceInstanceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v2/service_instance")
public class ServiceInstanceController extends BaseController {

    //TODO implement interface
    private ServiceInstanceService serviceInstanceService;

    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> params) {
        checkApiVersion(httpHeaders);
        //TODO implement method
        return null;
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> params,
            @Valid @RequestBody CreateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult, params);
        //TODO implement method
        return null;
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> params) {
        checkApiVersion(httpHeaders);
        //TODO implement method
        return null;
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam Map<String, String> params,
            @Valid @RequestBody UpdateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult, params);
        //TODO implement method
        return null;
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instance_id") String instanceId,
            @RequestParam Map<String, String> params) {
        checkApiVersion(httpHeaders);
        //TODO implement method
        return null;
    }
}
