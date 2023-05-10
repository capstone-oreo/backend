package com.oreo.backend.file.exception;

import com.oreo.backend.common.exception.CustomException;
import com.oreo.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidFileException extends CustomException {

    public InvalidFileException(String message) {
        super(ErrorCode.INVALID_FILE, HttpStatus.BAD_REQUEST, message);
    }
}
