package com.jyp.miniboard.member.service;

import com.jyp.miniboard.member.domain.Member;
import com.jyp.miniboard.member.dto.SignInResponse;
import com.jyp.miniboard.member.dto.SignUpRequest;
import com.jyp.miniboard.member.dto.SignUpResponse;
import com.jyp.miniboard.member.exception.MemberErrorResult;
import com.jyp.miniboard.member.exception.MemberException;
import com.jyp.miniboard.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignServiceTest {

    @InjectMocks
    private SignService signService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder encoder;
    private final Long id = -1L;
    private final String name = "name";
    private final String email = "name@email.com";
    private final String password = "password";

    @Test
    void 회원가입실패_중복된이메일() {
        // given
        doReturn(true).when(memberRepository).existsByEmail(email);
        final SignUpRequest request = new SignUpRequest(name, email, password);

        // when then
        assertThatThrownBy(() -> {
            signService.signUp(request);
        }).isInstanceOf(MemberException.class)
                .satisfies(e -> {
                    final MemberException ex = (MemberException) e;
                    assertThat(ex.getErrorResult()).isEqualTo(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
                });

        // verify
        verify(memberRepository, times(1)).existsByEmail(email);
    }

    @Test
    void 회원가입성공() {
        // given
        doReturn(false).when(memberRepository).existsByEmail(email);
        doReturn(member()).when(memberRepository).save(any(Member.class));
        doReturn("encoded password").when(encoder).encode(any(String.class));
        final SignUpRequest request = new SignUpRequest(name, email, password);

        // when
        SignUpResponse result = signService.signUp(request);

        // then
        assertThat(result.id()).isNotNull();
    }

    @Test
    void 로그인실패_이메일없음() {
        // given
        doReturn(Optional.empty()).when(memberRepository).findByEmail(email);

        // when then
        final MemberException result = assertThrows(MemberException.class, () -> signService.signIn(email, password));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NO_MEMBER_EMAIL);
    }

    @Test
    void 로그인실패_비밀번호틀림() {
        // given
        doReturn(Optional.of(member())).when(memberRepository).findByEmail(email);
        
        // when
        final MemberException result = assertThrows(MemberException.class, () -> signService.signIn(email, "wrong password"));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.NO_PWD_CORRECT);
    }

    @Test
    void 로그인성공() {
        // given
        doReturn(Optional.of(member())).when(memberRepository).findByEmail(email);

        // when
        SignInResponse result = signService.signIn(email, password);

        // then
        assertThat(result.id()).isEqualTo(-1L);
        assertThat(result.email()).isEqualTo(email);
    }

    private Member member() {
        return Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
