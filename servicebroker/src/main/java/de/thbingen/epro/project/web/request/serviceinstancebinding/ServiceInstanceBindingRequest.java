/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.request.serviceinstancebinding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thbingen.epro.project.web.request.OsbRequest;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class ServiceInstanceBindingRequest extends OsbRequest {

    @NonNull
    @JsonIgnore
    private String instanceId;

    @NonNull
    @JsonIgnore
    private String bindingId;

    public ServiceInstanceBindingRequest(@NonNull Map<String, String> httpHeaders, @NonNull String instanceId, @NonNull String bindingId) {
        super(httpHeaders);
        this.instanceId = instanceId;
        this.bindingId = bindingId;
    }
}
