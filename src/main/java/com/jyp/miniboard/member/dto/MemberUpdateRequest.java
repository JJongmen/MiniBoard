package com.jyp.miniboard.member.dto;

public record MemberUpdateRequest(
        String name,
        String password,
        String newPassword
) {
}
