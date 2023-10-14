package com.jyp.miniboard.controller;

import com.google.gson.Gson;
import com.jyp.miniboard.dto.sign_in.SignInRequest;
import com.jyp.miniboard.dto.sign_up.SignUpRequest;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.service.SignService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @SpyBean
    private SignService signService;

    @Test
    void mockMvc가null이아님() {
        assertThat(mockMvc).isNotNull();
        assertThat(gson).isNotNull();
    }

    @Nested
    class 회원가입테스트 {

        @ParameterizedTest(name = "{1}")
        @MethodSource("provideInvalidSignUpRequests")
        void 회원가입실패_잘못된요청값(SignUpRequest request, String description) throws Exception {
            // given
            final String url = "/api/v1/sign-up";

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isBadRequest());
        }

        private static Stream<Arguments> provideInvalidSignUpRequests() {
            return Stream.of(
                    Arguments.of(new SignUpRequest(null, "name@email.com", "password"), "name이 없음"),
                    Arguments.of(new SignUpRequest("name", null, "password"), "email이 없음"),
                    Arguments.of(new SignUpRequest("name", "name@email,com", null), "password가 없음"),
                    Arguments.of(new SignUpRequest("name", "wrong email form", "password"), "email 형식이 잘못됨"),
                    Arguments.of(new SignUpRequest("", "name@email.com", "password"), "name이 빈 문자열임"),
                    Arguments.of(new SignUpRequest("name", "", "password"), "email이 빈 문자열임"),
                    Arguments.of(new SignUpRequest("name", "name@email.com", ""), "password가 빈 문자열임")
            );
        }

        @Test
        void 회원가입실패_이미등록된email임() throws Exception {
            // given
            final String url = "/api/v1/sign-up";
            final SignUpRequest request = new SignUpRequest("name", "name@email.com", "password");
            doThrow(new MemberException(MemberErrorResult.DUPLICATED_MEMBER_EMAIL)).when(signService).signUp(request);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(MemberErrorResult.DUPLICATED_MEMBER_EMAIL.name()));
        }

        @Test
        void 회원가입성공() throws Exception {
            // given
            final String url = "/api/v1/sign-up";
            final SignUpRequest request = new SignUpRequest("name", "name@email.com", "password");

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
    class 로그인테스트 {

        @ParameterizedTest(name = "{1}")
        @MethodSource("provideInvalidSignInRequests")
        void 로그인실패_잘못된요청값(SignInRequest request, String description) throws Exception {
            // given
            final String url = "/api/v1/sign-in";

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isBadRequest());
        }

        private static Stream<Arguments> provideInvalidSignInRequests() {
            return Stream.of(
                    Arguments.of(new SignInRequest(null, "password"), "email이 없음"),
                    Arguments.of(new SignInRequest("name@email.com", null), "password가 없음"),
                    Arguments.of(new SignInRequest("wrong email form", "password"), "email 형식이 잘못됨"),
                    Arguments.of(new SignInRequest("", "password"), "email이 빈 문자열임"),
                    Arguments.of(new SignInRequest("name@email.com", ""), "password가 빈 문자열임")
            );
        }

        @Test
        void 로그인실패_존재하지않는이메일() throws Exception {
            // given
            final String url = "/api/v1/sign-in";
            final SignInRequest request = new SignInRequest("name@email.com", "password");
            doThrow(new MemberException(MemberErrorResult.INVALID_ID_OR_PASSWORD)).when(signService).signIn(request);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(MemberErrorResult.INVALID_ID_OR_PASSWORD.name()));
        }

        @Test
        void 로그인실패_틀린비밀번호() throws Exception {
            // given
            final String url = "/api/v1/sign-in";
            final SignInRequest request = new SignInRequest("name@email.com", "wrong password");
            doThrow(new MemberException(MemberErrorResult.INVALID_ID_OR_PASSWORD)).when(signService).signIn(request);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .content(gson.toJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            resultActions.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(MemberErrorResult.INVALID_ID_OR_PASSWORD.name()));
        }
    }
}