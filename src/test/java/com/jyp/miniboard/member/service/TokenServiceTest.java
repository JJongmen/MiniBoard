package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.TokenCreateRequest;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

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
        final TokenCreateRequest tokenCreateRequest = new TokenCreateRequest(-1L, "name", "email");

        // when
        final String result = tokenService.createToken(tokenCreateRequest);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 멤버ID조회() {
        // given
        final TokenCreateRequest tokenCreateRequest = new TokenCreateRequest(-1L, "name", "email");
        final String token = tokenService.createToken(tokenCreateRequest);
        final String authorizationHeader = "Bearer " + token;

        // when
        final Long result = tokenService.getMemberId(authorizationHeader);

        // then
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void 멤버조회실패_없는ID() {
        // given
        final TokenCreateRequest tokenCreateRequest = new TokenCreateRequest(-1L, "name", "email");
        final String token = tokenService.createToken(tokenCreateRequest);
        final String authorizationHeader = "Bearer " + token;
        doReturn(Optional.empty()).when(memberRepository).findById(any());

        // when
        final MemberException result = assertThrows(MemberException.class, () -> tokenService.getMember(authorizationHeader));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NO_MEMBER_ID);
    }
}