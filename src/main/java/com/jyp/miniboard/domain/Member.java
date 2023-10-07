package com.jyp.miniboard.domain;

import com.jyp.miniboard.common.MemberType;
import com.jyp.miniboard.dto.sign_up.SignUpRequest;
import com.jyp.miniboard.dto.member.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberType type;

    public static Member from(SignUpRequest request, PasswordEncoder encoder) {
        return Member.builder()
                .name(request.name())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .type(MemberType.USER)
                .build();
    }

    public void update(MemberUpdateRequest newMember, PasswordEncoder encoder) {
        this.password = newMember.newPassword() == null || newMember.newPassword().isBlank()
                ? this.password : encoder.encode(newMember.newPassword());
        this.name = newMember.name();
    }
}
