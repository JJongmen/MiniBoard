package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.MemberSaveResponse;
import com.jyp.miniboard.member.dto.MemberUpdateRequest;
import com.jyp.miniboard.member.dto.MemberUpdateResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

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

    @Transactional
    public MemberUpdateResponse updateMember(final Long id, MemberUpdateRequest request) {
        return memberRepository.findById(id)
                .filter(member -> encoder.matches(request.password(), member.getPassword()))
                .map(member -> {
                    member.update(request, encoder);
                    return MemberUpdateResponse.of(true, member);
                })
                .orElseThrow(() -> new NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }
}
