package com.jyp.miniboard.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(final String name, final String email, final String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
        }
    }
}
