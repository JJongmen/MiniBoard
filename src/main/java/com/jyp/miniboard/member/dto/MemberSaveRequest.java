package com.jyp.miniboard.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberSaveRequest(
        @NotNull
        String name,
        @NotNull @Email
        String email,
        @NotNull
        String password) {
}
