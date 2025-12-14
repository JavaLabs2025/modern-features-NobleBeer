package org.lab.infrastructure.rest.exception.handling;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.common.dto.ApiResponse;
import org.lab.common.dto.ErrorResponseDto;
import org.lab.common.exception.ProcessingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.lab.common.util.StringUtils.trim;
import static org.lab.infrastructure.rest.exception.handling.ErrorHandlingExtensions.getValidationErrorMsg;

@Slf4j
@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String CLIENT_INCORRECT_REQUEST_BASE_TEXT = "Некорректный входящий запрос";
    private static final String INNER_SERVICE_EXCEPTION_BASE_TEXT = "Внутренняя ошибка";

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TypeMismatchException.class,
            HttpMessageConversionException.class,
            MissingRequestValueException.class})
    public ErrorResponseDto handleWebExchangeBindException(Exception e) {
        return prepareErrorResponse(HttpStatus.BAD_REQUEST.name(),
                buildErrorMessage(CLIENT_INCORRECT_REQUEST_BASE_TEXT, e.getMessage()), e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg;
        if (e.getCause() instanceof JsonParseException || e.getCause() instanceof JsonMappingException) {
            msg = ((JsonProcessingException) e.getCause()).getOriginalMessage();
        } else {
            msg = e.getMostSpecificCause().getMessage();
        }

        return prepareErrorResponse(HttpStatus.BAD_REQUEST.name(),
                buildErrorMessage(CLIENT_INCORRECT_REQUEST_BASE_TEXT, msg), e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponseDto handleContentTypeMismatchException(HttpMediaTypeNotSupportedException e) {
        var supportedMediaTypes = e.getSupportedMediaTypes();
        var detailedMessage = String.format(
                "Переданный Content-Type не поддерживается, необходимо указать один из поддерживаемых %s",
                supportedMediaTypes
        );

        return prepareErrorResponse(HttpStatus.BAD_REQUEST.name(),
                buildErrorMessage(CLIENT_INCORRECT_REQUEST_BASE_TEXT, detailedMessage), e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponseDto handle(BindException e) {
        return prepareErrorResponse(HttpStatus.BAD_REQUEST.name(),
                buildErrorMessage(CLIENT_INCORRECT_REQUEST_BASE_TEXT, getValidationErrorMsg(e)), e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponseDto handle(ConstraintViolationException e) {
        return prepareErrorResponse(HttpStatus.BAD_REQUEST.name(),
                buildErrorMessage(CLIENT_INCORRECT_REQUEST_BASE_TEXT, getValidationErrorMsg(e)), e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponseDto handleOthersException(Exception e) {
        return prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                buildErrorMessage(CLIENT_INCORRECT_REQUEST_BASE_TEXT, INNER_SERVICE_EXCEPTION_BASE_TEXT), e);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler
    public ApiResponse<Void> handleProcessingException(ProcessingException e) {
        return ApiResponse.error(ApiResponse.ErrorDto.builder()
                .title("Техническая ошибка")
                .code(e.getErrorCode())
                .text(e.getMessage())
                .build());
    }

    private ErrorResponseDto prepareErrorResponse(String errorCode, String errorMessage, Exception e) {
        var errorUID = UUID.randomUUID();
        log.error("{} {}", errorUID, errorMessage, e);

        return ErrorResponseDto.builder()
                .errorUID(errorUID)
                .errorCode(errorCode)
                .errorMessage(trim(errorMessage, 512))
                .build();
    }

    private static String buildErrorMessage(String errorName, String errorDescription) {
        return String.format("%s %s", errorName, errorDescription);
    }
}
