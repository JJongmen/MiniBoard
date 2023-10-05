package com.jyp.miniboard.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberException extends RuntimeException{

    private final MemberErrorResult errorResult;
}
