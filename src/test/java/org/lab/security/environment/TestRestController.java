package org.lab.security.environment;

import org.lab.common.dto.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("securityRestController")
@RequestMapping
public class TestRestController {

    public static final String AUTH_REQUIRED_TEST_REST_CONTROLLER_ENDPOINT_PATH = "/test";
    public static final String SPECIFIC_ROLE_ONLY_TEST_REST_CONTROLLER_ENDPOINT_PATH = "/projects/{projectName}";
    public static final String PUBLIC_TEST_REST_CONTROLLER_ENDPOINT_PATH = "/auth/any";

    @GetMapping(AUTH_REQUIRED_TEST_REST_CONTROLLER_ENDPOINT_PATH)
    public ApiResponse<String> getOperationWithAuthPermissionRestriction() {
        return ApiResponse.success("test-result");
    }

    @PreAuthorize("""
        hasAuthority('PROJECT:' + #projectName + ':DEVELOPER') 
        or 
        hasAuthority('PROJECT:' + #projectName + ':TESTER')
    """)
    @GetMapping(SPECIFIC_ROLE_ONLY_TEST_REST_CONTROLLER_ENDPOINT_PATH)
    public ApiResponse<String> getOperationWithAdminPermissionRestriction(
            @PathVariable String projectName
    ) {
        return ApiResponse.success("test-result");
    }

    @GetMapping(PUBLIC_TEST_REST_CONTROLLER_ENDPOINT_PATH)
    public ApiResponse<String> getOperationWithoutPermissionRestriction() {
        return ApiResponse.success("test-result");
    }

}
