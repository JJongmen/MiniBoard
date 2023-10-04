package com.jyp.miniboard.member;

import com.google.gson.Gson;
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
public class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;
    @Mock
    private MemberService memberService;
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
        gson = new Gson();
    }

    @Test
    void mockMvc가Null이아님() {
        assertThat(memberController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }
}
