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
import com.jyp.miniboard.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignServiceTest {

    private SignService signService;
    @Mock
    private MemberRepository memberRepository;
    @Spy
    private PasswordEncoder encoder;
    private TokenProvider tokenProvider;
    private final Long id = -1L;
    private final String name = "name";
    private final String email = "name@email.com";
    private final String password = "password";

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider("thisissecretkeythisissecretkeythisissecretkey", 1L, "issuer");
        signService = new SignService(memberRepository, tokenProvider, encoder);
    }

    @Test
    void signService가null이아님() {
        assertThat(signService).isNotNull();
    }

    @Test
    void 회원가입실패_중복된이메일() {
        // given
        doThrow(DataIntegrityViolationException.class).when(memberRepository).flush();

        // when
        final MemberException result = assertThrows(MemberException.class, () ->
                signService.signUp(new SignUpRequest(name, email, password))
        );

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.DUPLICATED_MEMBER_EMAIL);
    }

    @Test
    void 회원가입성공() {
        // given
        final SignUpRequest request = new SignUpRequest(name, email, password);
        doReturn(member()).when(memberRepository).save(any(Member.class));

        // when
        SignUpResponse result = signService.signUp(request);

        // then
        assertThat(result.id()).isEqualTo(id);
    }

    @Test
    void 로그인실패_이메일없음() {
        // given
        final SignInRequest request = new SignInRequest(email, password);
        doReturn(Optional.empty()).when(memberRepository).findByEmail(email);

        // when then
        final MemberException result = assertThrows(MemberException.class, () -> {
            signService.signIn(request);
        });

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.INVALID_ID_OR_PASSWORD);
    }

    @Test
    void 로그인실패_비밀번호틀림() {
        // given
        final SignInRequest request = new SignInRequest(email, "wrong password");
        doReturn(Optional.of(member())).when(memberRepository).findByEmail(email);
        doReturn(false).when(encoder).matches(eq("wrong password"), anyString());

        // when
        final MemberException result = assertThrows(MemberException.class, () -> {
            signService.signIn(request);
        });

        // then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.INVALID_ID_OR_PASSWORD);
    }

    @Test
    void 로그인성공() {
        // given
        doReturn(Optional.of(member())).when(memberRepository).findByEmail(email);
        doReturn(true).when(encoder).matches(anyString(), anyString());
        final SignInRequest request = new SignInRequest(email, password);

        // when
        SignInResponse result = signService.signIn(request);

        // then
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.token()).isNotNull();
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
