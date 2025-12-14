package org.lab.user.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message, Exception exception) {
        super(message, exception);
    }
}
