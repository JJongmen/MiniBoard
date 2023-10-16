package com.jyp.miniboard.dto.post;

import com.jyp.miniboard.domain.Post;

public record PostSummary(
        Long id,
        String title,
        String writerName
) {

    public static PostSummary from(Post post) {
        return new PostSummary(
                post.getId(),
                post.getTitle(),
                post.getWriter().getName()
        );
    }
}