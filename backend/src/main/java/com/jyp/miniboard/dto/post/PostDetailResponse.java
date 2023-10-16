package com.jyp.miniboard.dto.post;

import com.jyp.miniboard.domain.Post;

public record PostDetailResponse(
        String title,
        String content,
        String writerName
) {
    public static PostDetailResponse from(final Post post) {
        return new PostDetailResponse(
                post.getTitle(),
                post.getContent(),
                post.getWriter().getName()
        );
    }
}
