package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.web.request.serviceinstance.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.serviceinstance.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.serviceinstance.CreateServiceInstanceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/service_instance")
public class ServiceInstanceController extends BaseController {


    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId) {


        return null;
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @RequestParam(value = "accepts_incomplete", required = false) boolean acceptIncomplete,
            @Valid @RequestBody CreateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult);

        return null;
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @Valid @RequestBody DeleteServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult);

        return null;
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instanceId") String instanceId,
            @Valid @RequestBody UpdateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkAndComplete(httpHeaders, request, bindingResult);


        return null;
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable("instance_id") String instanceId,
            @RequestParam(value = "service_id", required = false) String serviceId,
            @RequestParam(value = "plan_id", required = false) String planId,
            @RequestParam(value = "operation", required = false) String operation) {
        checkApiVersion(httpHeaders);

        return null;
    }
}
