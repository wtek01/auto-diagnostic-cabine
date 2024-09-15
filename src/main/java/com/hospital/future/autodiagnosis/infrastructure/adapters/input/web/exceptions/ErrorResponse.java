package com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response object")
public class ErrorResponse {
    @Schema(description = "Error code", example = "INVALID_ARGUMENT")
    private String code;

    @Schema(description = "Error message", example = "Health index cannot be negative")
    private String message;
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}