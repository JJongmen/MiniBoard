package com.jyp.miniboard.controller;

import com.google.gson.Gson;
import com.jyp.miniboard.common.GlobalExceptionHandler;
import com.jyp.miniboard.dto.EditPostRequest;
import com.jyp.miniboard.dto.PostDetailResponse;
import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.exception.PostErrorResult;
import com.jyp.miniboard.exception.PostException;
import com.jyp.miniboard.security.JwtAuthenticationFilter;
import com.jyp.miniboard.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
public class PostControllerTest {

    @MockBean
    private PostService postService;
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @BeforeEach
    void setUp() {
        final PostController postController = new PostController(postService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(postController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();
    }

    @Test
    void mockMvc가null이아님() {
        assertThat(mockMvc).isNotNull();
    }

    @Nested
    class 게시글작성 {
        @ParameterizedTest(name = "{1}")
        @MethodSource("provideInvalidCreatePostRequests")
        @WithMockUser(authorities = "USER")
        void 게시글작성실패_잘못된파라미터(CreatePostRequest request, String description) throws Exception {
            // given
            final String url = "/api/v1/posts";

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)

            );

            // then
            resultActions.andExpect(status().isBadRequest());
        }

        private static Stream<Arguments> provideInvalidCreatePostRequests() {
            return Stream.of(
                    Arguments.of(new CreatePostRequest(null, "content"), "제목이 null임"),
                    Arguments.of(new CreatePostRequest("", "content"), "제목이 빈문자열임"),
                    Arguments.of(new CreatePostRequest("title", null), "내용이 null임"),
                    Arguments.of(new CreatePostRequest("title", ""), "내용이 빈문자열임")
            );
        }

        @Test
        @WithMockUser(username = "1")
        void 게시글작성성공() throws Exception {
            // given
            final String url = "/api/v1/posts";
            final CreatePostRequest request = new CreatePostRequest("title", "content");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isCreated());
        }
    }

    @Nested
    class 게시글조회 {

        @Test
        void 게시글조회실패_postId가문자열임() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final String postId = "invalidstring";

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.get(url, postId)
            );

            // then
            resultActions.andExpect(status().isBadRequest());
        }

        @Test
        void 게시글조회실패_존재하지않는게시글임() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final Long postId = 1L;
            doThrow(new PostException(PostErrorResult.NOT_FOUND_POST)).when(postService).getPostDetail(postId);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.get(url, postId)
            );

            // then
            resultActions.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(PostErrorResult.NOT_FOUND_POST.name()));
        }

        @Test
        void 게시글조회성공() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            doReturn(new PostDetailResponse("title", "content", "name")).when(postService).getPostDetail(1L);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.get(url, 1L)
            );

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("title"))
                    .andExpect(jsonPath("$.content").value("content"))
                    .andExpect(jsonPath("$.writerName").value("name"));
        }
    }

    @Nested
    class 게시글수정 {

        @ParameterizedTest(name = "{1}")
        @MethodSource("provideInvalidEditPostRequests")
        @WithMockUser(username = "1")
        void 게시글수정실패_잘못된파라미터(final EditPostRequest request, final String description) throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.put(url, postId)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isBadRequest());
        }

        private static Stream<Arguments> provideInvalidEditPostRequests() {
            return Stream.of(
                    Arguments.of(new EditPostRequest(null, "content"), "제목이 null임"),
                    Arguments.of(new EditPostRequest("", "content"), "제목이 빈문자열임"),
                    Arguments.of(new EditPostRequest("title", null), "내용이 null임"),
                    Arguments.of(new EditPostRequest("title", ""), "내용이 빈문자열임")
            );
        }

        @Test
        @WithMockUser(username = "1")
        void 게시글수정실패_존재하지않는게시글임() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;
            final EditPostRequest request = new EditPostRequest("title", "content");
            doThrow(new PostException(PostErrorResult.NOT_FOUND_POST)).when(postService).editPost(1L, postId, request);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.put(url, postId)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(PostErrorResult.NOT_FOUND_POST.name()));
        }

        @Test
        @WithMockUser(username = "1")
        void 게시글수정실패_본인의게시글이아님() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;
            final EditPostRequest request = new EditPostRequest("title", "content");
            doThrow(new MemberException(MemberErrorResult.NOT_MATCH_MEMBER)).when(postService).editPost(1L, postId, request);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.put(url, postId)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.code").value(MemberErrorResult.NOT_MATCH_MEMBER.name()));
        }

        @Test
        @WithMockUser(username = "1")
        void 게시글수정성공() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;
            final EditPostRequest request = new EditPostRequest("title", "content");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.put(url, postId)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isNoContent());
        }
    }

    @Nested
    class 게시글삭제 {
        @Test
        @WithMockUser(username = "1")
        void 게시글삭제실패_존재하지않는게시글임() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;
            doThrow(new PostException(PostErrorResult.NOT_FOUND_POST)).when(postService).deletePost(1L, postId);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(url, postId)
            );

            // then
            resultActions.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(PostErrorResult.NOT_FOUND_POST.name()));
        }

        @Test
        @WithMockUser(username = "1")
        void 게시글삭제실패_본인의게시글이아님() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;
            doThrow(new MemberException(MemberErrorResult.NOT_MATCH_MEMBER)).when(postService).deletePost(1L, postId);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(url, postId)
            );

            // then
            resultActions.andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.code").value(MemberErrorResult.NOT_MATCH_MEMBER.name()));
        }

        @Test
        @WithMockUser(username = "1")
        void 게시글삭제성공() throws Exception {
            // given
            final String url = "/api/v1/posts/{postId}";
            final long postId = 1L;

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.delete(url, postId)
            );

            // then
            resultActions.andExpect(status().isNoContent());
        }
    }
}
