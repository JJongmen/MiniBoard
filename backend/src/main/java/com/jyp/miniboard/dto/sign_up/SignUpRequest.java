package com.jyp.miniboard.dto.sign_up;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank @Email
        String email,
        @NotNull @NotBlank
        String password) {
}
