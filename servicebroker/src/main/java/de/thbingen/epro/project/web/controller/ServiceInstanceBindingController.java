/**
 * Rest controller for service binding request.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServiceInstanceBindingController {

    @GetMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> getServiceInstanceBinding(@PathVariable String instanceId, @PathVariable String bindingId) {
        return null;
    }

    @DeleteMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> deleteServiceInstanceBinding(@PathVariable String instanceId, @PathVariable String bindingId) {
        return null;
    }

    @PutMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
    public ResponseEntity<?> bindServiceInstance(@PathVariable String instanceId, @PathVariable String bindingId) {
        return null;
    }

    @GetMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}/last_operation")
    public ResponseEntity<?> lastOperation(@PathVariable String instanceId, @PathVariable String bindingId) {
        return null;
    }

}
