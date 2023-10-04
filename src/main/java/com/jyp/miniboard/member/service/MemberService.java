package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.MemberSaveResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberSaveResponse join(final String name, final String email, final String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
        }

        final Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        final Member savedMember = memberRepository.save(member);

        return new MemberSaveResponse(savedMember.getId());
    }
}
