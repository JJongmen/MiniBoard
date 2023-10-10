package com.jyp.miniboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PostErrorResult {
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
