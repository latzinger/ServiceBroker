package de.thbingen.epro.project.web.request.serviceinstancebinding;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
public class LastOperationServiceInstanceBindingRequest extends ServiceInstanceBindingRequest {

    public LastOperationServiceInstanceBindingRequest(@NonNull Map<String, String> httpHeaders, @NonNull String instanceId, @NonNull String bindingId) {
        super(httpHeaders, instanceId, bindingId);
    }
}
