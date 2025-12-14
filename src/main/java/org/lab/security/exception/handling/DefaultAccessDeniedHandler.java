package org.lab.security.exception.handling;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.lab.common.dto.ApiResponse;
import org.lab.common.exception.ProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class DefaultAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    private static final ApiResponse<?> ERROR_RESPONSE = ApiResponse.error(ApiResponse.ErrorDto.builder()
            .title("Техническая ошибка")
            .code(ProcessingException.DLMSB_01)
            .text("Нет доступа")
            .build());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        setUpResponse(response);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        setUpResponse(response);
    }

    private void setUpResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(getSerializedResponse(ERROR_RESPONSE).getBytes(StandardCharsets.UTF_8));
    }

    @SneakyThrows
    private String getSerializedResponse(ApiResponse<?> response) {
        return objectMapper.writeValueAsString(response);
    }
}
