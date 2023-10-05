package com.jyp.miniboard.member.controller;

import com.jyp.miniboard.member.dto.MemberLoginRequest;
import com.jyp.miniboard.member.dto.MemberLoginResponse;
import com.jyp.miniboard.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            @RequestBody @Valid final MemberLoginRequest memberLoginRequest,
            HttpServletRequest request) {
        final Long memberId = loginService.login(memberLoginRequest.email(), memberLoginRequest.password());

        HttpSession session = request.getSession();
        if (!session.isNew()) {
            session.invalidate();
            session = request.getSession();
        }
        session.setAttribute("memberId", memberId);

        final MemberLoginResponse memberLoginResponse = new MemberLoginResponse(memberId);
        return ResponseEntity.ok(memberLoginResponse);
    }
}
