package com.oreo.backend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("1000"),
    RUNTIME_ERROR("1001"),
    VALIDATE_EXCEPTION("1002"),
    TYPE_MISMATCH("1003");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
