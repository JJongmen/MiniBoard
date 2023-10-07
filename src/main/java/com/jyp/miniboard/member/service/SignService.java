package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.SignInRequest;
import com.jyp.miniboard.member.dto.SignInResponse;
import com.jyp.miniboard.member.dto.SignUpRequest;
import com.jyp.miniboard.member.dto.SignUpResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import com.jyp.miniboard.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

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

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest request) {
        final Member findMember = memberRepository.findByEmail(request.email())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new MemberException(MemberErrorResult.INVALID_ID_OR_PASSWORD));
        final String token = tokenProvider.createToken(String.format("%s:%s", findMember.getId(), findMember.getType()));
        return new SignInResponse(findMember.getName(), findMember.getType(), token);
    }
}
