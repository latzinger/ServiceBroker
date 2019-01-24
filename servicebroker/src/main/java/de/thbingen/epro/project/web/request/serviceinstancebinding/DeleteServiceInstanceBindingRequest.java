package de.thbingen.epro.project.web.request.serviceinstancebinding;

import lombok.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
public class DeleteServiceInstanceBindingRequest extends ServiceInstanceBindingRequest {

    public DeleteServiceInstanceBindingRequest(@NonNull Map<String, String> httpHeaders, @NonNull String instanceId, @NonNull String bindingId) {
        super(httpHeaders, instanceId, bindingId);
    }

}
