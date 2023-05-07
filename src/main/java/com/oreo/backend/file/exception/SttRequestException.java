package com.oreo.backend.file.exception;

import com.oreo.backend.exception.CustomException;
import com.oreo.backend.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class SttRequestException extends CustomException {

    public SttRequestException(String message) {
        super(ErrorCode.STT_REQUEST, HttpStatus.BAD_REQUEST, message);
    }
}
