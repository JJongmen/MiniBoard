package com.jyp.miniboard.member;

public record MemberSaveResponse(
        Long id,
        String name,
        String email,
        String password
) {
}