package com.oreo.backend.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("1000"),
    RUNTIME_ERROR("1001"),
    VALIDATE_EXCEPTION("1002"),
    TYPE_MISMATCH("1003"),
    STT_REQUEST("1004"),
    INVALID_FILE("1005"),
    INVALID_FILE_EXTENSION("1006"),
    FILE_NOT_FOUND("1007");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
