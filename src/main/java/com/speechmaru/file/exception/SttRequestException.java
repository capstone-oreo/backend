package com.speechmaru.file.exception;

import com.speechmaru.common.exception.CustomException;
import com.speechmaru.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class SttRequestException extends CustomException {

    public SttRequestException(String message) {
        super(ErrorCode.STT_REQUEST, HttpStatus.BAD_REQUEST, message);
    }
}
