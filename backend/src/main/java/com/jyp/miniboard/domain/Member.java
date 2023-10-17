package com.jyp.miniboard.domain;

import com.jyp.miniboard.common.MemberType;
import com.jyp.miniboard.dto.sign_up.SignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
@Comment("회원")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Comment("회원 ID")
    private Long id;
    @Column(name = "name", nullable = false)
    @Comment("회원 이름")
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    @Comment("회원 이메일 (중복 불가)")
    private String email;
    @Column(name = "password", nullable = false)
    @Comment("회원 비밀번호")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @Comment("회원 종류")
    private MemberType type;
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public static Member from(SignUpRequest request, PasswordEncoder encoder) {
        return Member.builder()
                .name(request.name())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .type(MemberType.USER)
                .build();
    }
}
