package com.jyp.miniboard.member.repository;

import com.jyp.miniboard.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA 관련된 설정만 로드
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    private final String name = "name";
    private final String email = "email";
    private final String password = "password";

    @Test
    void MemberRepository가Null이아님() {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    void 멤버등록() {
        // given
        final Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        // when
        final Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(password);
    }

    @Test
    void 멤버존재여부() {
        // given
        final Member member = Member.builder().email(email).build();
        memberRepository.save(member);

        // when
        boolean isExist = memberRepository.existsByEmail(email);

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    void 이메일가진멤버조회() {
        // given
        final Member member = Member.builder().email(email).build();
        memberRepository.save(member);

        // when
        final Optional<Member> optionalMember = memberRepository.findByEmail(email);

        // then
        assertThat(optionalMember.isPresent()).isTrue();
        assertThat(optionalMember.get().getEmail()).isEqualTo(email);
    }
}
