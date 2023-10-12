package com.jyp.miniboard.dto.sign_up;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotNull
        String name,
        @NotNull @Email
        String email,
        @NotNull
        String password) {
}
