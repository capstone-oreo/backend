package com.speechmaru.file.exception;

import com.speechmaru.common.exception.CustomException;
import com.speechmaru.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidFileException extends CustomException {

    public InvalidFileException(String message) {
        super(ErrorCode.INVALID_FILE, HttpStatus.BAD_REQUEST, message);
    }
}
