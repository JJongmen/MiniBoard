package com.jyp.miniboard.dto;

import com.jyp.miniboard.domain.Post;

import java.util.List;

public record GetPostListResponse(
        List<PostSummary> posts
) {
    public static GetPostListResponse from(final List<Post> posts) {
        return new GetPostListResponse(
                posts.stream()
                        .map(post -> new PostSummary(
                                post.getId(),
                                post.getTitle(),
                                post.getWriter().getName()))
                        .toList());
    }

    public record PostSummary(
            Long id,
            String title,
            String writerName
    ) {

    }
}
