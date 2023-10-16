package com.jyp.miniboard.service;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;
import com.jyp.miniboard.dto.post.EditPostRequest;
import com.jyp.miniboard.dto.post.PostDetailResponse;
import com.jyp.miniboard.dto.post.PostSummary;
import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.dto.post.CreatePostResponse;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.exception.PostErrorResult;
import com.jyp.miniboard.exception.PostException;
import com.jyp.miniboard.repository.MemberRepository;
import com.jyp.miniboard.repository.PostRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Spy
    private MemberRepository memberRepository;
    @Spy
    private PostRepository postRepository;
    private final long postId = -1L;
    private final long memberId = -1L;


    @Test
    void postService가null이아님() {
        assertThat(postService).isNotNull();
    }

    @Nested
    class 게시글작성 {

        @Test
        void 게시글작성실패_존재하지않는회원() {
            // given
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
    }

    @Nested
    class 게시글조회 {
        @Test
        void 게시글조회실패_존재하지않는게시물() {
            // given
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

    @Nested
    class 게시글수정 {

        @Test
        void 게시글수정실패_존재하지않는회원() {
            // given
            final EditPostRequest request = new EditPostRequest("title", "content");
            doThrow(new MemberException(MemberErrorResult.NOT_FOUND_MEMBER)).when(memberRepository).findById(memberId);

            // when
            final MemberException result = assertThrows(MemberException.class, () ->
                    postService.editPost(memberId, postId, request));

            // then
            assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NOT_FOUND_MEMBER);
        }

        @Test
        void 게시글수정실패_존재하지않는게시글() {
            // given
            final EditPostRequest request = new EditPostRequest("title", "content");
            doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
            doThrow(new PostException(PostErrorResult.NOT_FOUND_POST)).when(postRepository).findById(postId);

            // when
            final PostException result = assertThrows(PostException.class, () ->
                    postService.editPost(memberId, postId, request));

            // then
            assertThat(result.getErrorResult()).isEqualTo(PostErrorResult.NOT_FOUND_POST);
        }

        @Test
        void 게시글수정실패_본인의게시글이아님() {
            // given
            final EditPostRequest request = new EditPostRequest("title", "content");
            doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
            doReturn(Optional.of(postWithMember(Member.builder().id(-999L).build()))).when(postRepository).findById(postId);

            // when
            final MemberException result = assertThrows(MemberException.class, () ->
                    postService.editPost(memberId, postId, request));

            // then
            assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NOT_MATCH_MEMBER);
        }

        @Test
        void 게시글수정성공() {
            // given
            final EditPostRequest request = new EditPostRequest("changed title", "changed content");
            doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
            final Post post = post();
            doReturn(Optional.of(post)).when(postRepository).findById(postId);

            // when
            postService.editPost(memberId, postId, request);

            // then
            assertThat(post.getTitle()).isEqualTo("changed title");
            assertThat(post.getContent()).isEqualTo("changed content");
        }
    }

    @Nested
    class 게시글삭제 {
        @Test
        void 게시글삭제실패_존재하지않는회원() {
            // given
            doThrow(new MemberException(MemberErrorResult.NOT_FOUND_MEMBER)).when(memberRepository).findById(memberId);

            // when
            final MemberException result = assertThrows(MemberException.class, () ->
                    postService.deletePost(memberId, postId));

            // then
            assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NOT_FOUND_MEMBER);
        }

        @Test
        void 게시글삭제실패_존재하지않는게시글() {
            // given
            doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
            doThrow(new PostException(PostErrorResult.NOT_FOUND_POST)).when(postRepository).findById(postId);

            // when
            final PostException result = assertThrows(PostException.class, () ->
                    postService.deletePost(memberId, postId));

            // then
            assertThat(result.getErrorResult()).isEqualTo(PostErrorResult.NOT_FOUND_POST);
        }

        @Test
        void 게시글삭제실패_본인의게시글이아님() {
            // given
            doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
            doReturn(Optional.of(postWithMember(Member.builder().id(-999L).build()))).when(postRepository).findById(postId);

            // when
            final MemberException result = assertThrows(MemberException.class, () ->
                    postService.deletePost(memberId, postId));

            // then
            assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NOT_MATCH_MEMBER);
        }

        @Test
        void 게시글삭제성공() {
            // given
            doReturn(Optional.of(member())).when(memberRepository).findById(memberId);
            final Post post = post();
            doReturn(Optional.of(post)).when(postRepository).findById(postId);

            // when
            postService.deletePost(memberId, postId);

            // then
            verify(postRepository, times(1)).delete(post);
        }
    }

    @Nested
    class 게시글목록조회 {
        @Test
        void 게시글목록조회성공_page와size기본값() {
            // given
            final List<Post> posts = Arrays.asList(post(), post(), post());
            final Page<Post> postPage = new PageImpl<>(posts);
            doReturn(postPage).when(postRepository).findAll(any(PageRequest.class));
            final PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            final Page<PostSummary> result = postService.getPostList(pageRequest);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getTotalElements()).isEqualTo(3);
            assertThat(result.getTotalPages()).isEqualTo(1);
            assertThat(result.getContent().get(0).title()).isEqualTo("title");
        }
    }

    private Member member() {
        return Member.builder()
                .id(memberId)
                .name("name")
                .build();
    }

    private Post post() {
        return Post.builder()
                .id(postId)
                .title("title")
                .content("content")
                .writer(member())
                .build();
    }

    private Post postWithMember(final Member member) {
        return Post.builder()
                .id(postId)
                .title("title")
                .content("content")
                .writer(member)
                .build();
    }
}
