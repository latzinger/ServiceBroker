package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.web.exception.InvalidRequestException;
import de.thbingen.epro.project.web.request.CreateServiceInstanceRequest;
import de.thbingen.epro.project.web.response.CreateServiceInstanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/service_instance")
public class ServiceInstanceController extends BaseController {


    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchServiceInstance(){
        return null;
    }

    @PutMapping(value = "/{instanceId}")
    public ResponseEntity<CreateServiceInstanceResponse> provisonServiceInstance(@PathVariable("instanceId") String instanceId, @RequestParam(value = "accepts_incomplete", required = false) boolean acceptIncomplete, @Valid @RequestBody CreateServiceInstanceRequest request, BindingResult bindingResult){
        checkRequestValidity(bindingResult);

        return null;
    }

    @DeleteMapping(value = "/{instanceId}")
    public ResponseEntity<?> deleteServiceInstance(@PathVariable("instanceId") String instanceId, @Valid @RequestBody CreateServiceInstanceRequest request, BindingResult bindingResult){
        return null;
    }

    @PatchMapping(value = "/{instanceId}")
    public ResponseEntity<?> updateServiceInstance(@PathVariable("instanceId") String instanceId, @Valid @RequestBody CreateServiceInstanceRequest request, BindingResult bindingResult){
        return null;
    }

    @GetMapping(value = "/{instanceId}/last_operation")
    public ResponseEntity<?> lastOperation(@PathVariable("instanceId") String instanceId, @Valid @RequestBody CreateServiceInstanceRequest request, BindingResult bindingResult){
        return null;
    }
}
