package com.oreo.backend.exception;

import lombok.Getter;

@Getter
public record ErrorResponse(String errorCode, String message) {

}
