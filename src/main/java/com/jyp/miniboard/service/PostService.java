package com.jyp.miniboard.service;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;
import com.jyp.miniboard.dto.EditPostRequest;
import com.jyp.miniboard.dto.GetPostListResponse;
import com.jyp.miniboard.dto.PostDetailResponse;
import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.dto.post.CreatePostResponse;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.exception.PostErrorResult;
import com.jyp.miniboard.exception.PostException;
import com.jyp.miniboard.repository.MemberRepository;
import com.jyp.miniboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(final Long memberId,
                                         final CreatePostRequest request) {
        final Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NOT_FOUND_MEMBER));

        final Post postForSave = request.toEntity(writer);

        final Post savedPost = postRepository.save(postForSave);
        return new CreatePostResponse(savedPost.getId());
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorResult.NOT_FOUND_POST));

        return PostDetailResponse.from(post);
    }

    @Transactional
    public void editPost(final Long memberId,
                         final Long postId,
                         final EditPostRequest editPostRequest) {
        final Member editor = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NOT_FOUND_MEMBER));
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorResult.NOT_FOUND_POST));

        if (!post.isWrittenBy(editor)) {
            throw new MemberException(MemberErrorResult.NOT_MATCH_MEMBER);
        }

        post.edit(editPostRequest.title(), editPostRequest.content());
    }

    @Transactional
    public void deletePost(final Long memberId, final Long postId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NOT_FOUND_MEMBER));
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorResult.NOT_FOUND_POST));

        if (!post.isWrittenBy(member)) {
            throw new MemberException(MemberErrorResult.NOT_MATCH_MEMBER);
        }

        postRepository.delete(post);
    }

    public GetPostListResponse getPostList() {
        final List<Post> posts = postRepository.findAll();
        return GetPostListResponse.from(posts);
    }
}
