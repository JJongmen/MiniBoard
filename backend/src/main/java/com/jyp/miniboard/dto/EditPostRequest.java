package com.jyp.miniboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditPostRequest(
        @NotNull @NotBlank
        String title,
        @NotNull @NotBlank
        String content) {
}
