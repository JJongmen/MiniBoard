package com.jyp.miniboard.controller;

import com.google.gson.Gson;
import com.jyp.miniboard.dto.sign_up.SignUpRequest;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.service.SignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

    @Test
    void 회원가입실패_name이null임() throws Exception {
        // given
        final String url = "/api/v1/sign-up";
        final SignUpRequest request = new SignUpRequest(null, "name@email.com", "password");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 회원가입실패_email이null임() throws Exception {
        // given
        final String url = "/api/v1/sign-up";
        final SignUpRequest request = new SignUpRequest("name", null, "password");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 회원가입실패_password가null임() throws Exception {
        // given
        final String url = "/api/v1/sign-up";
        final SignUpRequest request = new SignUpRequest("name", "name@email.com", null);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void 회원가입실패_email형식이아님() throws Exception {
        // given
        final String url = "/api/v1/sign-up";
        final SignUpRequest request = new SignUpRequest("name", "wrong email form", "password");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
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
                .andExpectAll(jsonPath("$.code").value("DUPLICATED_MEMBER_EMAIL"),
                        jsonPath("$.message").value("이미 존재하는 이메일입니다."));
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