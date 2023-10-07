package com.jyp.miniboard.member.controller;

import com.jyp.miniboard.member.dto.*;
import com.jyp.miniboard.member.service.SignService;
import com.jyp.miniboard.member.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;
    private final TokenService tokenService;

    @PostMapping("/api/v1/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        final SignUpResponse signUpResponse = signService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping("/api/v1/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody @Valid final SignInRequest signInRequest) {
        final SignInResponse signInResponse = signService.signIn(signInRequest);
        return ResponseEntity.ok(signInResponse);
    }
}