package com.jyp.miniboard.repository;

import com.jyp.miniboard.common.JpaAuditingConfig;
import com.jyp.miniboard.common.MemberType;
import com.jyp.miniboard.domain.Member;
import com.jyp.miniboard.domain.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    private Member member;

    @Test
    void postRepository가null이아님() {
        assertThat(postRepository).isNotNull();
    }

    @BeforeEach
    void 회원등록() {
        member = memberRepository.save(member());
    }

    @Test
    void 게시글등록() {
        // given
        final Post post = post();
        final LocalDateTime now = LocalDateTime.now();

        // when
        final Post result = postRepository.save(post);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getWriter().getName()).isEqualTo("name");
        assertThat(result.getCreatedAt()).isAfter(now);
        assertThat(result.getUpdatedAt()).isAfter(now);
    }

    private Post post() {
        return Post.builder()
                .title("title")
                .content("content")
                .writer(member)
                .build();
    }

    private Member member() {
        return Member.builder()
                .name("name")
                .email("name@email.com")
                .password("password")
                .type(MemberType.USER)
                .build();
    }
}
