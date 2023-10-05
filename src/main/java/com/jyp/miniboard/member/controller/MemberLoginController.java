package com.jyp.miniboard.member.controller;

import com.jyp.miniboard.member.dto.MemberLoginRequest;
import com.jyp.miniboard.member.dto.MemberLoginResponse;
import com.jyp.miniboard.member.service.LoginService;
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

    @PostMapping("/api/v1/members/login")
    public ResponseEntity<MemberLoginResponse> login(
            @RequestBody @Valid final MemberLoginRequest memberLoginRequest) {
        return null;
    }
}
