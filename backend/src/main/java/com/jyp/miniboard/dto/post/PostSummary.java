package com.jyp.miniboard.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jyp.miniboard.domain.Post;

import java.time.LocalDateTime;

public record PostSummary(
        Long id,
        String title,
        String writerName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

    public static PostSummary from(Post post) {
        return new PostSummary(
                post.getId(),
                post.getTitle(),
                post.getWriter().getName(),
                post.getCreatedAt()
        );
    }
}