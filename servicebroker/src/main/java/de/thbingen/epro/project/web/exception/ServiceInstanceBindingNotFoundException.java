package de.thbingen.epro.project.web.exception;

import lombok.*;

/**
 * ServiceInstanceBindingNotFoundException
 * thrown when a Binding was not found
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceInstanceBindingNotFoundException extends RuntimeException {

    private String bindingId;

    @Override
    public String getMessage() {
        return "ServiceInstanceBinding not found: binding_id = " + bindingId;
    }
}
