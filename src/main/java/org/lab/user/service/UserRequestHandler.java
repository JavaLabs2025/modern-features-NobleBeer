package org.lab.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.infrastructure.rest.TokenInfo;
import org.lab.security.service.CustomUserDetailsService;
import org.lab.security.service.JwtService;
import org.lab.user.controller.dto.LoginRequestDto;
import org.lab.user.controller.dto.LoginResponseDto;
import org.lab.user.controller.dto.RegisterNewUserRequestDto;
import org.lab.user.exception.RegistrationException;
import org.lab.user.exception.UserAlreadyExistsException;
import org.lab.user.service.mapper.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserRequestHandler {

    private final UserMapper userMapper;
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void registerNewUser(RegisterNewUserRequestDto registerNewUserRequestDto) {
        try {
            log.info("Начат процесс регистрации пользователя {}", registerNewUserRequestDto.getUsername());
            if (userDetailsService.loadUserByUsername(registerNewUserRequestDto.getUsername()) != null) {
                throw new UserAlreadyExistsException("Пользователь с данным никнеймом уже существует");
            }

            var passwordHash = passwordEncoder.encode(registerNewUserRequestDto.getPassword());

            var user = userMapper.toUser(registerNewUserRequestDto, passwordHash);
            userService.save(user);

            log.info("Процесс регистрации пользователя завершен");
        } catch (Exception e) {
            log.error("Произошла ошибка при регистрации пользователя: {}", e.getMessage(), e);
            throw new RegistrationException("Не получилось зарегистрировать пользователя", e);
        }
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        var user = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());

        if (isInvalidPassword(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }

        var token = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .tokenType(TokenInfo.JWT_TOKEN_TYPE)
                .accessToken(token)
                .user(LoginResponseDto.UserDetailsDto.builder()
                        .username(user.getUsername())
                        .build())
                .build();
    }

    private boolean isInvalidPassword(String requestRawPassword, String passwordHash) {
        return !passwordEncoder.matches(requestRawPassword, passwordHash);
    }

}
