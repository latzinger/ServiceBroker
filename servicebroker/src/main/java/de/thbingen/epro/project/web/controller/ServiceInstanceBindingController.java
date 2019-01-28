/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.data.service.ServiceInstanceBindingService;
import de.thbingen.epro.project.web.request.serviceinstancebinding.CreateServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.DeleteServiceInstanceBindingRequest;
import de.thbingen.epro.project.web.request.serviceinstancebinding.LastOperationServiceInstanceBindingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v2/service_instances")
public class ServiceInstanceBindingController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceInstanceBindingController.class);

    @Autowired
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
            @RequestParam Map<String, String> parameters) {

        checkApiVersion(httpHeaders);

        DeleteServiceInstanceBindingRequest request =
                new DeleteServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setParameters(parameters);

        //TODO implement method

        return null;
    }

    @PutMapping(path = "/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> createServiceInstanceBinding(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters,
            @Valid @RequestBody CreateServiceInstanceBindingRequest request) {

        checkApiVersion(httpHeaders);
        request.setHttpHeaders(httpHeaders.toSingleValueMap());
        request.setParameters(parameters);

        //TODO implement method
        return null;
    }

    @GetMapping(path = "/{instanceId}/service_bindings/{bindingId}/last_operation")
    public ResponseEntity<?> lastOperation(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable String instanceId,
            @PathVariable String bindingId,
            @RequestParam Map<String, String> parameters) {

        checkApiVersion(httpHeaders);

        LastOperationServiceInstanceBindingRequest request =
                new LastOperationServiceInstanceBindingRequest(httpHeaders.toSingleValueMap(), instanceId, bindingId);
        request.setParameters(parameters);

        //TODO implement method
        return null;
    }

}
