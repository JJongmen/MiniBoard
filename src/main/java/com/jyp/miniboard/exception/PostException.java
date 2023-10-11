package com.jyp.miniboard.exception;

public class PostException extends BaseException {
    public PostException(final BaseErrorResult errorResult) {
        super(errorResult);
    }
}
