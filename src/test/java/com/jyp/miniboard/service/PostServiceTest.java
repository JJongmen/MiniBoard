package com.jyp.miniboard.service;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;
import com.jyp.miniboard.dto.PostDetailResponse;
import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.dto.post.CreatePostResponse;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.exception.PostErrorResult;
import com.jyp.miniboard.exception.PostException;
import com.jyp.miniboard.repository.MemberRepository;
import com.jyp.miniboard.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Spy
    private MemberRepository memberRepository;
    @Spy
    private PostRepository postRepository;
    private final long memberId = -1L;



    @Test
    void postService가null이아님() {
        assertThat(postService).isNotNull();
    }

    @Test
    void 게시글작성실패_존재하지않는회원() {
        // given
        final long memberId = -1L;
        final CreatePostRequest request = new CreatePostRequest("title", "content");
        doReturn(Optional.empty()).when(memberRepository).findById(memberId);

        // when
        final MemberException result = assertThrows(MemberException.class, () ->
                postService.createPost(memberId, request));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NOT_FOUND_MEMBER);
    }

    @Test
    void 게시글작성성공() {
        // given
        final CreatePostRequest request = new CreatePostRequest("title", "content");
        doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
        doReturn(post()).when(postRepository).save(any(Post.class));

        // when
        final CreatePostResponse result = postService.createPost(memberId, request);

        // then
        assertThat(result.id()).isNotNull();
    }

    private Member member() {
        return Member.builder()
                .id(memberId)
                .name("name")
                .build();
    }

    private Post post() {
        return Post.builder()
                .id(-1L)
                .title("title")
                .content("content")
                .writer(member())
                .build();
    }

    @Test
    void 게시글조회실패_존재하지않는게시물() {
        // given
        final long postId = 1L;
        doReturn(Optional.empty()).when(postRepository).findById(postId);

        // when
        final PostException result = assertThrows(PostException.class, () -> {
            postService.getPostDetail(postId);
        });

        // then
        assertThat(result.getErrorResult()).isEqualTo(PostErrorResult.NOT_FOUND_POST);
    }

    @Test
    void 게시글조회성공() {
        // given
        final long postId = 1L;
        doReturn(Optional.of(post())).when(postRepository).findById(postId);

        // when
        final PostDetailResponse result = postService.getPostDetail(postId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("title");
        assertThat(result.content()).isEqualTo("content");
        assertThat(result.writerName()).isEqualTo("name");
    }
}
