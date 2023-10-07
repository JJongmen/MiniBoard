package com.jyp.miniboard.member.dto;

public record SignInResponse(
        Long id,
        String name,
        String email
) {
}
