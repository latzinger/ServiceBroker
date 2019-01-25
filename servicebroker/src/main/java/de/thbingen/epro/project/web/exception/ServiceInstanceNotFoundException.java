/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.exception;

import lombok.Data;

@Data
public class ServiceInstanceNotFoundException extends RuntimeException {
    private String serviceInstanceId;

}
