package com.jyp.miniboard.member.controller;

import com.jyp.miniboard.member.dto.MemberLoginRequest;
import com.jyp.miniboard.member.dto.MemberLoginResponse;
import com.jyp.miniboard.member.dto.TokenCreateRequest;
import com.jyp.miniboard.member.service.LoginService;
import com.jyp.miniboard.member.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/api/v1/members/login")
    public ResponseEntity<MemberLoginResponse> login(
            @RequestBody @Valid final MemberLoginRequest memberLoginRequest) {
        final MemberLoginResponse memberLoginResponse = loginService.login(
                memberLoginRequest.email(),
                memberLoginRequest.password());

        TokenCreateRequest tokenCreateRequest = new TokenCreateRequest(
                memberLoginResponse.id(),
                memberLoginResponse.name(),
                memberLoginResponse.email());
        tokenService.createToken(tokenCreateRequest);

        return ResponseEntity.ok(memberLoginResponse);
    }
}
