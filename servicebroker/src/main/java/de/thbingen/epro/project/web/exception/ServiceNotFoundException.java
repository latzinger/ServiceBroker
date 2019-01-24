/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
