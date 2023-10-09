package com.jyp.miniboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostException extends RuntimeException {
    private final PostErrorResult errorResult;
}
