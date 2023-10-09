package com.jyp.miniboard.controller;

import com.google.gson.Gson;
import com.jyp.miniboard.common.GlobalExceptionHandler;
import com.jyp.miniboard.dto.post.CreatePostRequest;
import com.jyp.miniboard.security.JwtAuthenticationFilter;
import com.jyp.miniboard.service.PostService;
import org.junit.jupiter.api.BeforeEach;
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
