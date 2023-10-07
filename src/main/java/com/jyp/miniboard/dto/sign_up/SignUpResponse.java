package com.jyp.miniboard.dto.sign_up;

import com.jyp.miniboard.domain.Member;

public record SignUpResponse(
        Long id
) {

    public static SignUpResponse from(final Member member) {
        return new SignUpResponse(member.getId());
    }
}