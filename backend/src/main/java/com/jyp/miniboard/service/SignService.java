package com.jyp.miniboard.service;

import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.dto.sign_in.SignInRequest;
import com.jyp.miniboard.dto.sign_in.SignInResponse;
import com.jyp.miniboard.dto.sign_up.SignUpRequest;
import com.jyp.miniboard.dto.sign_up.SignUpResponse;
import com.jyp.miniboard.exception.MemberErrorResult;
import com.jyp.miniboard.exception.MemberException;
import com.jyp.miniboard.repository.MemberRepository;
import com.jyp.miniboard.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        Member member = memberRepository.save(Member.from(request, encoder));
        try {
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new MemberException(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
        }
        return SignUpResponse.from(member);
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
