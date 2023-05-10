package com.oreo.backend.file.exception;

import com.oreo.backend.common.exception.CustomException;
import com.oreo.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class SttRequestException extends CustomException {

    public SttRequestException(String message) {
        super(ErrorCode.STT_REQUEST, HttpStatus.BAD_REQUEST, message);
    }
}
