package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.MemberLoginResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;
    @Mock
    private MemberRepository memberRepository;
    private final String email = "name@email.com";
    private final String password = "password";

    @Test
    void 로그인실패_이메일없음() {
        // given
        doReturn(Optional.empty()).when(memberRepository).findByEmail(email);

        // when then
        final MemberException result = assertThrows(MemberException.class, () -> loginService.login(email, password));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NO_MEMBER_EMAIL);
    }

    @Test
    void 로그인실패_비밀번호틀림() {
        // given
        doReturn(Optional.of(member())).when(memberRepository).findByEmail(email);
        
        // when
        final MemberException result = assertThrows(MemberException.class, () -> loginService.login(email, "wrong password"));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NO_PWD_CORRECT);
    }

    @Test
    void 로그인성공() {
        // given
        doReturn(Optional.of(member())).when(memberRepository).findByEmail(email);

        // when
        MemberLoginResponse result = loginService.login(email, password);

        // then
        assertThat(result.id()).isEqualTo(-1L);
        assertThat(result.email()).isEqualTo(email);
    }

    private Member member() {
        return Member.builder().id(-1L).email(email).password(password).build();
    }
}
