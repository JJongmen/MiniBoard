package com.jyp.miniboard.controller;

import com.jyp.miniboard.dto.sign_in.SignInRequest;
import com.jyp.miniboard.dto.sign_in.SignInResponse;
import com.jyp.miniboard.dto.sign_up.SignUpRequest;
import com.jyp.miniboard.dto.sign_up.SignUpResponse;
import com.jyp.miniboard.service.SignService;
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