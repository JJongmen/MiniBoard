package com.jyp.miniboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberException extends RuntimeException{

    private final MemberErrorResult errorResult;

}
