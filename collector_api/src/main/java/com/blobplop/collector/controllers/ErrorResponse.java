package com.blobplop.collector.controllers;

import com.blobplop.collector.domain.ActionStatus;
import com.blobplop.collector.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getStatus() == null || result.getStatus() == ActionStatus.INVALID) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getStatus() == ActionStatus.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }
}
