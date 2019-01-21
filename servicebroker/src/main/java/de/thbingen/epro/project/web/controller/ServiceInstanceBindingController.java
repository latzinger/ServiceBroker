/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.services.ServiceInstanceBindingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/service_instances")
public class ServiceInstanceBindingController extends BaseController {

    //TODO implement interface
    private ServiceInstanceBindingService serviceInstanceBindingService;

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> getServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId) {
        checkApiVersion(httpHeaders);
        //TODO implement method
        return null;
    }

    @DeleteMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> deleteServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam(value = "service_id") String serviceId,
            @RequestParam(value = "plan_id") String planId,
            @RequestParam(value = "accepts_incomplete", required = false) boolean acceptIncomplete) {
        checkApiVersion(httpHeaders);
        //TODO implement method
        return null;
    }

    @PutMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> createServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam(value = "accepts_incomplete", required = false) boolean acceptIncomplete,
            @Valid @RequestBody CreateServiceInstanceBindingRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult);
        //TODO implement method
        return null;
    }

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam(value = "service_id", required = false) String serviceId,
            @RequestParam(value = "plan_id") String planId,
            @RequestParam(value = "operation", required = false) String operation) {
        checkApiVersion(httpHeaders);
        //TODO implement method
        return null;
    }

}
