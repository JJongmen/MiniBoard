package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.TokenCreateRequest;
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

    public String createToken(TokenCreateRequest tokenCreateRequest) {
        Date now = new Date();
        JwtBuilder jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("id", tokenCreateRequest.id())
                .claim("name", tokenCreateRequest.name())
                .claim("email", tokenCreateRequest.email())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .setExpiration(new Date(now.getTime() + Duration.ofHours(1).toMillis()));
        return jwt.compact();
    }

    private Claims parseToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new MemberException(MemberErrorResult.NO_TOKEN);
        }
        String token = authorizationHeader.substring("Bearer ".length());

        try {
            return Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException("ExpiredToken");
        }
    }

    public Long getMemberId(String authorizationHeader) {
        final Claims token = parseToken(authorizationHeader);
        return Long.valueOf(token.get("id").toString());
    }

    public Member getMember(String authorizationHeader) {
        final Claims token = parseToken(authorizationHeader);
        final Long id = Long.valueOf(token.get("id").toString());
        final Optional<Member> optionalMember = memberRepository.findById(id);
        return optionalMember.orElseThrow(() ->
                new MemberException(MemberErrorResult.NO_MEMBER_ID));
    }
}
