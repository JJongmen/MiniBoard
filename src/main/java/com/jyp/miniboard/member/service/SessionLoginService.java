package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SessionLoginService implements LoginService {
    private final MemberRepository memberRepository;

    @Override
    public void login(final String email, final String password) {
        final Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NO_MEMBER_EMAIL));
        if (!password.equals(findMember.getPassword())) {
            throw new MemberException(MemberErrorResult.NO_PWD_CORRECT);
        }
    }
}
