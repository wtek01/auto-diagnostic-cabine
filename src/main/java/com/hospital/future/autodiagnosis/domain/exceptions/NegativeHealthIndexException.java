package com.hospital.future.autodiagnosis.domain.exceptions;

public class NegativeHealthIndexException extends RuntimeException {
    public NegativeHealthIndexException(String message) {
        super(message);
    }
}