package de.thbingen.epro.project.web.exception;

import lombok.Data;
@Data
public class ServiceInstanceNotFoundException extends RuntimeException {
    private String serviceInstanceId;

}
