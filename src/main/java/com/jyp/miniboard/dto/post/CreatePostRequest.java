package com.jyp.miniboard.dto.post;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;

public record CreatePostRequest(
        String title,
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
