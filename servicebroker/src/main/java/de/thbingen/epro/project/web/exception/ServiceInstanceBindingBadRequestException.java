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
public class ServiceInstanceBindingBadRequestException extends RuntimeException {

    private String bindingId;
    private String requestBody;

    @Override
    public String getMessage() {
        return "ServiceInstanceBinding  is malformed or has missing mandatory data: binding_id = " + bindingId
                + ", requestBody = " + requestBody;
    }
}
