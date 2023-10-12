package com.jyp.miniboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorResult implements BaseErrorResult {
    INVALID_ID_OR_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 이메일 또는 비밀번호입니다."),
    DUPLICATED_MEMBER_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    UNKNOWN_EXCEPTION(HttpStatus.BAD_REQUEST, "알 수 없는 오류가 발생했습니다."),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_MATCH_MEMBER(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
