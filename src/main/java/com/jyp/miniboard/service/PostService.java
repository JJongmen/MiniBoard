package com.jyp.miniboard.service;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;
import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.dto.post.CreatePostResponse;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.repository.MemberRepository;
import com.jyp.miniboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(final Long memberId, final CreatePostRequest request) {
        final Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NOT_FOUND_MEMBER));

        final Post postForSave = request.toEntity(writer);

        final Post savedPost = postRepository.save(postForSave);
        return new CreatePostResponse(savedPost.getId());
    }
}
