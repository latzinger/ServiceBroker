package de.thbingen.epro.project.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceInstanceResponse {
    @NotEmpty
    private String dashboardUrl;

    @NotEmpty
    private String operation;
}