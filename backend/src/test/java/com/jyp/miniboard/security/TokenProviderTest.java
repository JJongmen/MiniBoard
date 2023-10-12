package com.jyp.miniboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class TokenProviderTest {

    private TokenProvider tokenProvider;
    private final String secretKey = "thisisfakesecretkeythisisfakesecretkeythisisfakesecretkeythisisfakesecretkeythisisfakesecretkey";
    private final long expirationHours = 1L;
    private final String issuer = "issuer";


    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(secretKey, expirationHours, issuer);
    }

    @Test
    void tokenProvider가null이아님() {
        assertThat(tokenProvider).isNotNull();
    }

    @Test
    void 토큰생성성공() {
        // given
        final String userSpecification = "1:USER";

        // when
        final String result = tokenProvider.createToken(userSpecification);

        // then
        assertThat(result).isNotNull();
        assertThat(result.split("\\.").length).isEqualTo(3);
    }

    @Test
    void 토큰검증실패_잘못된토큰() {
        // given
        final String wrongToken = "wrong token";

        // when then
        assertThatThrownBy(() -> {
            tokenProvider.validateTokenAndGetSubject(wrongToken);
        }).isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void 토큰검증성공() {
        // given
        final String userSpecification = "1:USER";
        final String token = tokenProvider.createToken(userSpecification);

        // when
        final String result = tokenProvider.validateTokenAndGetSubject(token);

        // then
        assertThat(result).isEqualTo(userSpecification);
    }
}