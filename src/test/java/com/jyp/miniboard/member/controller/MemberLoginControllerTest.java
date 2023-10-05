package com.jyp.miniboard.member.controller;

import com.google.gson.Gson;
import com.jyp.miniboard.member.dto.MemberLoginRequest;
import com.jyp.miniboard.member.service.SessionLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberLoginControllerTest {

    @InjectMocks
    private MemberLoginController memberLoginController;
    @Mock
    private SessionLoginService sessionLoginService;
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberLoginController).build();
        gson = new Gson();
    }

    @Test
    void mockMvc가Null이아님() throws Exception {
        assertThat(memberLoginController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidLoginRequestParameter")
    void 로그인실패_잘못된파라미터(String email, String password) throws Exception {
        // given
        final String url = "/api/v1/members/login";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(new MemberLoginRequest(email, password)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidLoginRequestParameter() {
        return Stream.of(
                Arguments.of(null, "password"),
                Arguments.of("name@email.com", null),
                Arguments.of("wrong email", "password")
        );
    }
}
