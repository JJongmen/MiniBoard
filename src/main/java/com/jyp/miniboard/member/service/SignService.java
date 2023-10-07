package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.SignInResponse;
import com.jyp.miniboard.member.dto.SignUpRequest;
import com.jyp.miniboard.member.dto.SignUpResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public SignInResponse signIn(final String email, final String password) {
        final Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorResult.NO_MEMBER_EMAIL));
        if (!password.equals(findMember.getPassword())) {
            throw new MemberException(MemberErrorResult.NO_PWD_CORRECT);
        }

        return new SignInResponse(findMember.getId(), findMember.getName(), findMember.getEmail());
    }

    @Transactional
    public SignUpResponse signUp(final SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
        }

        final Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
        Member.from(request, encoder);
        final Member savedMember = memberRepository.save(member);

        return new SignUpResponse(savedMember.getId());
    }
}
