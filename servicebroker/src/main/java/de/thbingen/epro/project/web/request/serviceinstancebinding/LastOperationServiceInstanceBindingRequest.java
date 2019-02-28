package de.thbingen.epro.project.web.request.serviceinstancebinding;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

/**
 * Representing an LastOperationServiceInstanceBindingRequest containing all necessary information
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
public class LastOperationServiceInstanceBindingRequest extends ServiceInstanceBindingRequest {

    public LastOperationServiceInstanceBindingRequest(@NonNull Map<String, String> httpHeaders, @NonNull String instanceId, @NonNull String bindingId) {
        super(httpHeaders, instanceId, bindingId);
    }

    public Long getOperationId(){
        Map<String, String> requestParameters = getRequestParameters();
        String operation = requestParameters.get("operation");

        Long id = -1L;

        try{
            id= Long.parseLong(operation);
        } catch (NumberFormatException e){
        }
        return id;
    }
}
