package com.jyp.miniboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PostErrorResult {
    ALREADY_WRITTEN(HttpStatus.BAD_REQUEST, "이미 작성한 글입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
