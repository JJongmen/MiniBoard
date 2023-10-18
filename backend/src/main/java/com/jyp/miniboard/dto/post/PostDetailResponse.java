package com.jyp.miniboard.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jyp.miniboard.domain.Post;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PostDetailResponse(
        String title,
        String content,
        String writerName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
    public static PostDetailResponse from(final Post post) {
        return new PostDetailResponse(
                post.getTitle(),
                post.getContent(),
                post.getWriter().getName(),
                post.getCreatedAt()
        );
    }
}
