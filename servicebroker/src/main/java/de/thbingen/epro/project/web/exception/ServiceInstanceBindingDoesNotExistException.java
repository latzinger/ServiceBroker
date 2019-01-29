/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceInstanceBindingDoesNotExistException extends RuntimeException {

    private String bindingId;


    @Override
    public String getMessage() {
        return "ServiceInstanceBinding does not exist: binding_id = " + bindingId;
    }
}
