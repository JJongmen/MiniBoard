package com.jyp.miniboard.dto.sign_in;

import com.jyp.miniboard.common.MemberType;

public record SignInResponse(
        String name,
        MemberType type,
        String token
) {
}
