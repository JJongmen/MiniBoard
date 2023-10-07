package com.jyp.miniboard.dto.member;

public record MemberUpdateRequest(
        String name,
        String password,
        String newPassword
) {
}
