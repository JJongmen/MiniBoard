package com.jyp.miniboard.member.dto;

public record TokenCreateRequest(
        Long id,
        String name,
        String email) {
}
