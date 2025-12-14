package org.lab.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    boolean success;
    T data;
    ErrorDto error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static ApiResponse<Void> success() {
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }

    public static ApiResponse<Void> error(ErrorDto error) {
        return ApiResponse.<Void>builder()
                .success(false)
                .error(error)
                .build();
    }

    @Value
    @Builder
    @Jacksonized
    public static class ErrorDto {
        String title;
        @NotNull
        String code;
        String text;
    }
}
