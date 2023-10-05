package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.dto.MemberLoginResponse;

public interface LoginService {
    MemberLoginResponse login(String email, String password);
}
