package com.oreo.backend.record.exception;

import com.oreo.backend.common.exception.CustomException;
import com.oreo.backend.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends CustomException {

    public RecordNotFoundException(String message) {
        super(ErrorCode.FILE_NOT_FOUND, HttpStatus.BAD_REQUEST, message);
    }
}
