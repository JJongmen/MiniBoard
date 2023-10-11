package com.jyp.miniboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseException extends RuntimeException {
    private final BaseErrorResult errorResult;
}