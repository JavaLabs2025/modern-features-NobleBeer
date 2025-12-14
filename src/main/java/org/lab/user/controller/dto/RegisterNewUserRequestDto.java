package org.lab.user.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RegisterNewUserRequestDto {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{1,32}$")
    private String username;
    @NotNull
    @Pattern(regexp = "^[А-Яа-яЁё-]{1,32}$")
    private String firstName;
    @Pattern(regexp = "^[А-Яа-яЁё-]{0,32}$")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])[A-Za-z0-9[^A-Za-z0-9]]{6,21}$",
            message = "Пароль должен содержать не менее 6 символов, одну заглавную букву, одну цифру, один специальный символ"
    )
    private String password;
}
