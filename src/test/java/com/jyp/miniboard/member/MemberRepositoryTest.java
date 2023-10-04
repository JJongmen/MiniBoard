package com.jyp.miniboard.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA 관련된 설정만 로드
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void MemberRepository가Null이아님() {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    void 멤버등록() {
        // given
        final Member member = Member.builder()
                .name("name")
                .email("email")
                .password("password")
                .build();

        // when
        final Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getEmail()).isEqualTo("email");
        assertThat(result.getPassword()).isEqualTo("password");
    }
}
