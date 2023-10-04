package com.jyp.miniboard.member;

public class MemberException extends RuntimeException{

    private final MemberErrorResult errorResult;

    public MemberException(final MemberErrorResult errorResult) {
        this.errorResult = errorResult;
    }

    public MemberErrorResult getErrorResult() {
        return errorResult;
    }
}
