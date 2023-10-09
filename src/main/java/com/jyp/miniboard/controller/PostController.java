package com.jyp.miniboard.controller;

import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.dto.post.CreatePostResponse;
import com.jyp.miniboard.security.UserAuthorize;
import com.jyp.miniboard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    @UserAuthorize
    public ResponseEntity<CreatePostResponse> createPost(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreatePostRequest request) {
        final CreatePostResponse response = postService.createPost(getMemberId(user), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private static long getMemberId(final User user) {
        return Long.parseLong(user.getUsername());
    }
}
