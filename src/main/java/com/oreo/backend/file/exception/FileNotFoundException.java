package com.oreo.backend.file.exception;

import com.oreo.backend.common.exception.CustomException;
import com.oreo.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CustomException {

    public FileNotFoundException(String message) {
        super(ErrorCode.FILE_NOT_FOUND, HttpStatus.BAD_REQUEST, message);
    }
}
