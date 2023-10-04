package com.jyp.miniboard.member;

import jakarta.validation.constraints.NotNull;

public record MemberSaveRequest(
        @NotNull
        String name,
        String email,
        String password) {
}
