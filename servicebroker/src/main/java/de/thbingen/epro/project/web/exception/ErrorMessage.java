/**
 * Handling service broker error messages.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private String error;
    private String description;

}
