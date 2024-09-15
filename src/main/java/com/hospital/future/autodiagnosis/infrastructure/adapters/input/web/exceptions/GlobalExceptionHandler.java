package com.hospital.future.autodiagnosis.infrastructure.adapters.input.web;

import com.hospital.future.autodiagnosis.domain.exceptions.InvalidArgumentException;
import com.hospital.future.autodiagnosis.domain.exceptions.NegativeHealthIndexException;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgumentException(InvalidArgumentException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", "Invalid argument");
    }

    @ExceptionHandler(NegativeHealthIndexException.class)
    public ResponseEntity<ErrorResponse> handleNegativeHealthIndexException(NegativeHealthIndexException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "NEGATIVE_HEALTH_INDEX", "Negative health index");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT_TYPE", "Invalid argument type");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "An unexpected error occurred");
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String code, String message) {
        ErrorResponse errorResponse = new ErrorResponse(code, message);
        return new ResponseEntity<>(errorResponse, status);
    }
}