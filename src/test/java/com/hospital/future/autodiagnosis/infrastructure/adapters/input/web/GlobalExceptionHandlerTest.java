package com.hospital.future.autodiagnosis.infrastructure.adapters.input.web;

import com.hospital.future.autodiagnosis.domain.exceptions.InvalidArgumentException;
import com.hospital.future.autodiagnosis.domain.exceptions.NegativeHealthIndexException;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.exceptions.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.GlobalExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @ParameterizedTest
    @CsvSource({
            "InvalidArgumentException, INVALID_ARGUMENT, Invalid argument, 400",
            "NegativeHealthIndexException, NEGATIVE_HEALTH_INDEX, Negative health index, 400",
            "Exception, INTERNAL_SERVER_ERROR, An unexpected error occurred, 500"
    })
    void testExceptionHandling(String exceptionName, String expectedCode, String expectedMessage, int expectedStatus) {
        Exception exception = createException(exceptionName);
        ResponseEntity<ErrorResponse> response;

        if (exceptionName.equals("InvalidArgumentException")) {
            response = globalExceptionHandler.handleInvalidArgumentException((InvalidArgumentException) exception);
        } else if (exceptionName.equals("NegativeHealthIndexException")) {
            response = globalExceptionHandler.handleNegativeHealthIndexException((NegativeHealthIndexException) exception);
        } else {
            response = globalExceptionHandler.handleGlobalException(exception);
        }

        assertEquals(expectedStatus, response.getStatusCodeValue());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(expectedCode, errorResponse.getCode());
        assertEquals(expectedMessage, errorResponse.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"MethodArgumentTypeMismatchException, INVALID_ARGUMENT_TYPE, Invalid argument type, 400"})
    void testMethodArgumentTypeMismatchException(String exceptionName, String expectedCode, String expectedMessage, int expectedStatus) {
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodArgumentTypeMismatch(exception);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(expectedCode, errorResponse.getCode());
        assertEquals(expectedMessage, errorResponse.getMessage());
    }

    private Exception createException(String exceptionName) {
        switch (exceptionName) {
            case "InvalidArgumentException":
                return new InvalidArgumentException("Test invalid argument");
            case "NegativeHealthIndexException":
                return new NegativeHealthIndexException("Test negative health index");
            default:
                return new Exception("Test general exception");
        }
    }
}