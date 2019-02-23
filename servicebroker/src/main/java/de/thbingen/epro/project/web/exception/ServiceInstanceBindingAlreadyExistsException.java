package de.thbingen.epro.project.web.exception;

import de.thbingen.epro.project.data.model.ServiceInstanceBinding;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceInstanceBindingAlreadyExistsException extends RuntimeException {

    private ServiceInstanceBinding serviceInstanceBinding;
    private boolean matchingParameters;

    @Override
    public String getMessage() {
        return "ServiceInstanceBinding already exist: binding_id = " + serviceInstanceBinding.getId() +
                " matching requestParameters = " + matchingParameters;
    }

}
