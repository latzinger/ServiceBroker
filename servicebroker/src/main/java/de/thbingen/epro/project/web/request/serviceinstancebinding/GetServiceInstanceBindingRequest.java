package de.thbingen.epro.project.web.request.serviceinstancebinding;

import lombok.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
public class GetServiceInstanceBindingRequest extends ServiceInstanceBindingRequest {

    public GetServiceInstanceBindingRequest(@NonNull Map<String, String> httpHeaders, @NonNull String instanceId, @NonNull String bindingId) {
        super(httpHeaders, instanceId, bindingId);
    }

}
