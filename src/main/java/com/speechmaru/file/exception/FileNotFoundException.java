package com.speechmaru.file.exception;

import com.speechmaru.common.exception.CustomException;
import com.speechmaru.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CustomException {

    public FileNotFoundException(String message) {
        super(ErrorCode.FILE_NOT_FOUND, HttpStatus.BAD_REQUEST, message);
    }
}
