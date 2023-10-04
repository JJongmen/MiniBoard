package com.jyp.miniboard.member;

import jakarta.validation.constraints.NotNull;

public record MemberSaveRequest(
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String password) {
}
