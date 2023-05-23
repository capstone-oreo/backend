package com.speechmaru.record.exception;

import com.speechmaru.common.exception.CustomException;
import com.speechmaru.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends CustomException {

    public RecordNotFoundException(String message) {
        super(ErrorCode.FILE_NOT_FOUND, HttpStatus.BAD_REQUEST, message);
    }
}
