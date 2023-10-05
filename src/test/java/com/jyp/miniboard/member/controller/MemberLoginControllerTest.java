package com.jyp.miniboard.member.controller;

import com.google.gson.Gson;
import com.jyp.miniboard.member.service.SessionLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;

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
}
