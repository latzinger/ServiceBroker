package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.request.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.request.DeleteServiceInstanceRequest;
import de.thbingen.epro.project.web.request.UpdateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.CreateServiceInstanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/service_instance")
public class ServiceInstanceController extends BaseController {


    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(
            @RequestHeader("X-Broker-API-Version") String apiVersion,
            @PathVariable("instanceId") String instanceId) {
        return null;
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
            @PathVariable("instanceId") String instanceId,
            @RequestParam(value = "accepts_incomplete", required = false) boolean acceptIncomplete,
            @Valid @RequestBody CreateServiceInstanceRequest request,
            BindingResult bindingResult) {
        checkRequestValidity(bindingResult);

        return null;
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(@PathVariable("instanceId") String instanceId, @Valid @RequestBody DeleteServiceInstanceRequest request, BindingResult bindingResult) {

        return null;
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(@PathVariable("instanceId") String instanceId, @Valid @RequestBody UpdateServiceInstanceRequest request, BindingResult bindingResult) {

        return null;
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @PathVariable("instance_id") String instanceId,
            @RequestParam(value = "service_id", required = false) String serviceId,
            @RequestParam(value = "plan_id", required = false) String planId,
            @RequestParam(value = "operation", required = false) String operation
            ) {
        return null;
    }
}
