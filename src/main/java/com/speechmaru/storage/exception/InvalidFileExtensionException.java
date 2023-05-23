package com.speechmaru.storage.exception;

import com.speechmaru.common.exception.CustomException;
import com.speechmaru.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidFileExtensionException extends CustomException {

    public InvalidFileExtensionException(String message) {
        super(ErrorCode.INVALID_FILE_EXTENSION, HttpStatus.BAD_REQUEST, message);
    }
}
