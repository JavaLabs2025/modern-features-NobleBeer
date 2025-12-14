package org.lab.user.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String accessToken;
    private String tokenType;
    private UserDetailsDto user;

    @Getter
    @Builder
    public static class UserDetailsDto {
        private String username;
    }
}
