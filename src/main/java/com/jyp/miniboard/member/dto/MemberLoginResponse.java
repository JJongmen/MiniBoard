package com.jyp.miniboard.member.dto;

public record MemberLoginResponse(
        Long id,
        String name,
        String email
) {
}
