package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void tokenService가Null이아님() {
        assertThat(tokenService).isNotNull();
    }

    @Test
    void 토큰생성() {
        // given
        final Member member = Member.builder()
                .id(-1L)
                .name("name")
                .email("name@email.com")
                .build();

        // when
        final String result = tokenService.createToken(member);

        // then
        assertThat(result).isNotNull();
    }
}