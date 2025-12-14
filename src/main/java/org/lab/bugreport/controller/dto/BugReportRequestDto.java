package org.lab.bugreport.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class BugReportRequestDto {
    @NotBlank
    @Size(max = 255)
    private String title;
    @Size(max = 2000)
    private String description;
}
