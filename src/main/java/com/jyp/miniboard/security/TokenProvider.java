package com.jyp.miniboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@PropertySource("classpath:jwt.yml")
@Service
public class TokenProvider {
    private final String secretKey;
    private final long expirationHours;
    private final String issuer;

    public TokenProvider(
            @Value("${secret-key}") final String secretKey,
            @Value("${expiration-hours}") final long expirationHours,
            @Value("${issuer}") final String issuer) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
        this.issuer = issuer;
    }

    public String createToken(final String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
