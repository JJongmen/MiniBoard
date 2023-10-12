package com.jyp.miniboard.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorResult {
    HttpStatus getHttpStatus();
    String getMessage();
    String name();
}
