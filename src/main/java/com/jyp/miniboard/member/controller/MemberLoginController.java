package com.jyp.miniboard.member.controller;

import com.jyp.miniboard.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final LoginService loginService;
}
