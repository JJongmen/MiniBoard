package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final MemberRepository memberRepository;

    public String createToken(Member member) {
        Date now = new Date();
        JwtBuilder jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("id", member.getId())
                .claim("name", member.getName())
                .claim("email", member.getEmail())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .setExpiration(new Date(now.getTime() + Duration.ofHours(1).toMillis()));
        return jwt.compact();
    }
}
