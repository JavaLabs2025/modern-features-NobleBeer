package org.lab.security.service;

import org.lab.security.model.CustomUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import java.util.regex.Pattern;

@Component
public class ProjectAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final Pattern PROJECT_PATTERN =
            Pattern.compile("^/projects/([^/]+)/.*");

    @Override
    public AuthorizationDecision authorize(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        var request = context.getRequest();
        var matcher = PROJECT_PATTERN.matcher(request.getRequestURI());

        if (!matcher.matches()) {
            return new AuthorizationDecision(true);
        }

        var projectName = matcher.group(1);

        var auth = authentication.get();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails user)) {
            return new AuthorizationDecision(false);
        }

        return new AuthorizationDecision(
                user.hasProjectAccess(projectName)
        );
    }

    @Override
    @Deprecated
    public AuthorizationDecision check(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context
    ) {
        try {
            authorize(authentication, context);
            return new AuthorizationDecision(true);
        } catch (AccessDeniedException ex) {
            return new AuthorizationDecision(false);
        }
    }
}
