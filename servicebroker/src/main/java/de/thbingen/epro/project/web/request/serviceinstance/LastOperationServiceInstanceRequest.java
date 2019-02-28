package de.thbingen.epro.project.web.request.serviceinstance;

import lombok.Data;

/**
 * Request data class for get last operation
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */
@Data
public class LastOperationServiceInstanceRequest extends ServiceInstanceRequest {
    private String operationId;
}
