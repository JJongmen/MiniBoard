package com.jyp.miniboard.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    private final String name = "name";
    private final String email = "email";
    private final String password = "password";

    @Test
    void 회원가입실패_중복된이메일() {
        // given
        doReturn(true).when(memberRepository).existsByEmail(email);

        // when then
        Assertions.assertThatThrownBy(() -> {
                    memberService.join(name, email, password);
                }).isInstanceOf(MemberException.class)
                .satisfies(e -> {
                    final MemberException ex = (MemberException) e;
                    assertThat(ex.getErrorResult()).isEqualTo(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
                });

        // verify
        verify(memberRepository, times(1)).existsByEmail(email);
    }
}
