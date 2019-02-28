

package de.thbingen.epro.project.web.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Thrown if serivceId is not associated with any {@link de.thbingen.epro.project.servicebroker.services.OsbService}
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@Data
@RequiredArgsConstructor
public class ServiceNotFoundException extends RuntimeException {
    @NonNull
    private String serviceId;

    public ServiceNotFoundException(String serviceId, String message){
        super(message);
        this.serviceId = serviceId;
    }
}
