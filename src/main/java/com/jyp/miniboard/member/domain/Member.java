package com.jyp.miniboard.member.domain;

import com.jyp.miniboard.member.dto.MemberSaveRequest;
import com.jyp.miniboard.member.dto.MemberUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private MemberType type;

    public static Member from(MemberSaveRequest request, PasswordEncoder encoder) {
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
