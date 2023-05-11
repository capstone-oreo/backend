package com.oreo.backend.common.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @RequestParam valid
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        log.error(e.getClass().getSimpleName(), e);
        return getResponseEntity(ErrorCode.VALIDATE_EXCEPTION, e.getMessage(),
            HttpStatus.BAD_REQUEST);
    }

    // @RequestBody valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleRequestBodyValidationErrors(
        MethodArgumentNotValidException e) {
        log.error(e.getClass().getSimpleName(), e);
        return getValidErrorResponseEntity(e);
    }

    // @ModelAttribute valid
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleModelAttributeValidationErrors(BindException e) {
        log.error(e.getClass().getSimpleName(), e);
        return getValidErrorResponseEntity(e);
    }

    private ResponseEntity<ErrorResponse> getValidErrorResponseEntity(BindException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add("Object field '" + error.getField() + "' " + error.getDefaultMessage());
        }

        return getResponseEntity(ErrorCode.VALIDATE_EXCEPTION, errors.toString(),
            HttpStatus.BAD_REQUEST);
    }

    // Type Mismatch Validation
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleValidationTypeMismatchErrors(
        MethodArgumentTypeMismatchException e) {
        log.error(e.getClass().getSimpleName(), e);
        return getResponseEntity(ErrorCode.TYPE_MISMATCH, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Custom exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrors(final CustomException e) {
        log.error(e.getClass().getSimpleName(), e);
        return getResponseEntity(e.getErrorCode(), e.getMessage(), e.getHttpStatus());
    }

    // Custom Exception 에서 처리되지 않은 에러
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeError(final RuntimeException e) {
        log.error(e.getClass().getSimpleName(), e);
        return getResponseEntity(ErrorCode.RUNTIME_ERROR, e.getMessage(),
            HttpStatus.BAD_REQUEST);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unhandledException(final Exception e) {
        log.error(e.getClass().getSimpleName(), e);
        return getResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(ErrorCode errorCode, String message,
        HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse(errorCode.getErrorCode(), message);
        return new ResponseEntity<>(response, httpStatus);
    }
}
