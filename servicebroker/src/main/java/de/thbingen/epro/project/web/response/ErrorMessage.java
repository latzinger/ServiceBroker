package de.thbingen.epro.project.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Representing Error Message defined by openservicebrokerapi
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
@AllArgsConstructor
public class ErrorMessage {

    public static final String ASYNC_REQUIRED = "AsyncRequired";
    public static final String CONCURRENCY_ERROR = "ConcurrencyError";
    public static final String REQUIRES_APP = "RequiresApp";

    private String error;
    private String description;

}
