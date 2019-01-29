package de.thbingen.epro.project.web.exception;

import lombok.*;

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
