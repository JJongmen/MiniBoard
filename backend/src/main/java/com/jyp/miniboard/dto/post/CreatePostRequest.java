package com.jyp.miniboard.dto.post;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(
        @NotNull @NotBlank
        String title,
        @NotNull @NotBlank
        String content
) {
    public Post toEntity(Member writer) {
        return Post.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
