package org.lab.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lab.common.dto.ApiResponse;
import org.lab.user.controller.dto.LoginRequestDto;
import org.lab.user.controller.dto.LoginResponseDto;
import org.lab.user.controller.dto.RegisterNewUserRequestDto;
import org.lab.user.service.UserRequestHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserRequestHandler userRequestHandler;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid RegisterNewUserRequestDto registerNewUserRequestDto) {
        userRequestHandler.registerNewUser(registerNewUserRequestDto);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ApiResponse.success(userRequestHandler.login(loginRequestDto));
    }
}
