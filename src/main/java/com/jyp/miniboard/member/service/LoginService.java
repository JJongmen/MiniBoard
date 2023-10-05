package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.MemberLoginResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public MemberLoginResponse login(final String email, final String password) {
        final Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NO_MEMBER_EMAIL));
        if (!password.equals(findMember.getPassword())) {
            throw new MemberException(MemberErrorResult.NO_PWD_CORRECT);
        }

        return new MemberLoginResponse(findMember.getId(), findMember.getName(), findMember.getEmail());
    }
}
