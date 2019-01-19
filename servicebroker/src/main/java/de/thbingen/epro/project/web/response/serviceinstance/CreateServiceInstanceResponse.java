package de.thbingen.epro.project.web.response.serviceinstance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateServiceInstanceResponse {
    @NotEmpty
    private String dashboardUrl;

    @NotEmpty
    private String operation;
}