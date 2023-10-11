package com.jyp.miniboard.controller;

import com.jyp.miniboard.dto.EditPostRequest;
import com.jyp.miniboard.dto.PostDetailResponse;
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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable final Long postId) {
        final PostDetailResponse response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Void> editPost(
            @AuthenticationPrincipal User user,
            @PathVariable final Long postId,
            @RequestBody @Valid final EditPostRequest editPostRequest) {
        postService.editPost(getMemberId(user), postId, editPostRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Void> editPost(
            @AuthenticationPrincipal User user,
            @PathVariable final Long postId) {
        postService.deletePost(getMemberId(user), postId);
        return ResponseEntity.ok().build();
    }
}
