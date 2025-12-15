package org.lab.security.exception.handling;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationDeniedExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public void handle(AuthorizationDeniedException e) {
        throw e;
    }
}
