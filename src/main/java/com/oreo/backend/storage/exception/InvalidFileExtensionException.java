package com.oreo.backend.storage.exception;

import com.oreo.backend.common.exception.CustomException;
import com.oreo.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidFileExtensionException extends CustomException {

    public InvalidFileExtensionException(String message) {
        super(ErrorCode.INVALID_FILE_EXTENSION, HttpStatus.BAD_REQUEST, message);
    }
}
