package org.lab.infrastructure.rest.exception.handling;

import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorHandlingExtensions {

    public static String getValidationErrorMsg(BindException e) {
        return e.getAllErrors().stream()
                .map(ErrorHandlingExtensions::buildErrorMessage)
                .collect(Collectors.joining(";"));
    }

    public static String getValidationErrorMsg(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(error -> error.getPropertyPath() + " " + error.getMessage())
                .collect(Collectors.joining(";"));
    }

    private static String buildErrorMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return fieldError.getObjectName() +
                    "." +
                    fieldError.getField() +
                    " " +
                    fieldError.getDefaultMessage();
        }
        return error.toString();
    }
}
