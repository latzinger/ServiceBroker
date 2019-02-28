
package de.thbingen.epro.project.web.exception;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Thrown when {@link de.thbingen.epro.project.data.model.ServiceInstance} not found for given id
 *
 * @author jonashueg
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@RequiredArgsConstructor
@Getter
public class ServiceInstanceNotFoundException extends RuntimeException {

    @NonNull
    private String instanceId;

    @Override
    public String getMessage() {
        return "ServiceInstanceB not found: instance_id = " + instanceId;
    }

}
