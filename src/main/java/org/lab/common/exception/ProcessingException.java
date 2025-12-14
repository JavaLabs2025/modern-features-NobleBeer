package org.lab.common.exception;

import lombok.Getter;

@Getter
public class ProcessingException extends RuntimeException {
    public static final String DLMSB_01 = "DLMSB_01";

    private final String errorCode;

    public ProcessingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
