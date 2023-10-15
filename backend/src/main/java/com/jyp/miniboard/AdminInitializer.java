package com.jyp.miniboard;

import com.jyp.miniboard.common.MemberType;
import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
//@Component
public class AdminInitializer implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        memberRepository.save(Member.builder()
                .name("관리자")
                .email("admin@admin.com")
                .password(encoder.encode("password"))
                .type(MemberType.ADMIN)
                .build());
    }
}
