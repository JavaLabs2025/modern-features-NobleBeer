package org.lab.security;

import org.junit.jupiter.api.Test;
import org.lab.ApplicationIT;
import org.lab.infrastructure.rest.Headers;
import org.lab.infrastructure.rest.TokenInfo;
import org.lab.security.model.CustomUserDetails;
import org.springframework.http.MediaType;

import lombok.SneakyThrows;

import static org.lab.security.environment.TestRestController.SPECIFIC_ROLE_ONLY_TEST_REST_CONTROLLER_ENDPOINT_PATH;
import static org.lab.security.environment.TestRestController.AUTH_REQUIRED_TEST_REST_CONTROLLER_ENDPOINT_PATH;
import static org.lab.security.environment.TestRestController.PUBLIC_TEST_REST_CONTROLLER_ENDPOINT_PATH;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WebSecurityIT extends ApplicationIT {

    @Test
    @SneakyThrows
    void authRequiredEndpoint_anonymousUser_shouldReturnSuccessFalseAndCode200() {
        mockMvcWithSecurity.perform(get(AUTH_REQUIRED_TEST_REST_CONTROLLER_ENDPOINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "success": false,
                            "data": null,
                            "error": {
                                "title": "Техническая ошибка",
                                "code": "DLMSB_01",
                                "text": "Нет доступа"
                            }
                        }
                        """))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void adminOnlyEndpoint_hasInsufficientPermissions_shouldReturnSuccessFalseAndCode200() {
        var userDetails = UserDetailsBodyFactory.getUserDetailsWithPersistenceIdentifiersAndRole("PROJECT:test:NONE").build();
        var token = generateJwtToken(userDetails);
        when(userDetailsService.loadUserByUsername(userDetails.getUsername())).thenReturn(userDetails);

        mockMvcWithSecurity.perform(get(SPECIFIC_ROLE_ONLY_TEST_REST_CONTROLLER_ENDPOINT_PATH, "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Headers.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "success": false,
                            "data": null,
                            "error": {
                                "title": "Техническая ошибка",
                                "code": "DLMSB_01",
                                "text": "Нет доступа"
                            }
                        }
                        """))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void adminOnlyEndpoint_hasPermissions_shouldReturnSuccessTrueAndCode200() {
        var userDetails = UserDetailsBodyFactory.getUserDetailsWithPersistenceIdentifiersAndRole("PROJECT:test:DEVELOPER").build();
        var token = generateJwtToken(userDetails);
        when(userDetailsService.loadUserByUsername(userDetails.getUsername())).thenReturn(userDetails);

        mockMvcWithSecurity.perform(get(SPECIFIC_ROLE_ONLY_TEST_REST_CONTROLLER_ENDPOINT_PATH, "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Headers.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "success": true,
                            "data": "test-result",
                            "error": null
                        }
                        """))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void publicEndpoint_happyPath_shouldReturnSuccessTrueAndCode200() {
        mockMvcWithSecurity.perform(get(PUBLIC_TEST_REST_CONTROLLER_ENDPOINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "success": true,
                            "data": "test-result",
                            "error": null
                        }
                        """))
                .andDo(print());
    }

    private String generateJwtToken(CustomUserDetails userDetails) {
        return TokenInfo.JWT_TOKEN_PREFIX + jwtService.generateToken(userDetails);
    }
}
