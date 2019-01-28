package de.thbingen.epro.project.web.exception;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ServiceInstanceBindingNotFoundException extends RuntimeException {

    @NonNull
    private String bindingId;

    @Override
    public String getMessage() {
        return "ServiceInstanceBinding not found: binding_id = " + bindingId;
    }
}
