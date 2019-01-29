/**
 * TODO add description
 *
 * @author jonashueg
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.exception;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
