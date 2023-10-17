package com.jyp.miniboard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Table(name = "post")
@Comment("게시글")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @Comment("게시글 번호")
    private Long id;
    @Column(name = "title", nullable = false, length = 200)
    @Comment("게시글 제목")
    private String title;
    @Column(name = "content", nullable = false, length = 50000)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Comment("작성자 ID")
    private Member writer;

    public void edit(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isWrittenBy(final Member member) {
        return writer.getId().equals(member.getId());
    }
}
