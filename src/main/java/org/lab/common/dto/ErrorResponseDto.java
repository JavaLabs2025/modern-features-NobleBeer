package org.lab.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class ErrorResponseDto {
    @NotNull
    UUID errorUID;
    @NotNull
    String errorCode;
    String errorMessage;
}
