package com.jyp.miniboard.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA 관련된 설정만 로드
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void MemberRepository가Null이아님() {
        assertThat(memberRepository).isNotNull();
    }
}
