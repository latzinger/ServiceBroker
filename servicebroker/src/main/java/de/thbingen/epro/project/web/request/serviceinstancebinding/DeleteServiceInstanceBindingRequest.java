package de.thbingen.epro.project.web.request.serviceinstancebinding;

import lombok.*;

import java.util.Map;

/**
 * Representing an DeleteServiceInstanceBindingRequest containing all necessary information
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
public class DeleteServiceInstanceBindingRequest extends ServiceInstanceBindingRequest {

    public DeleteServiceInstanceBindingRequest(@NonNull Map<String, String> httpHeaders, @NonNull String instanceId, @NonNull String bindingId) {
        super(httpHeaders, instanceId, bindingId);
    }

}
