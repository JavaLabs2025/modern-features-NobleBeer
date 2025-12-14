package org.lab.user.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class LoginRequestDto {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{1,32}$")
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])[A-Za-z0-9[^A-Za-z0-9]]{6,21}$")
    private String password;
}
