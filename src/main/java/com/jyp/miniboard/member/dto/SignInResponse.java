package com.jyp.miniboard.member.dto;

import com.jyp.miniboard.member.domain.MemberType;

public record SignInResponse(
        String name,
        MemberType type,
        String token
) {
}
