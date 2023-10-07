package com.jyp.miniboard.member.dto;

import com.jyp.miniboard.member.domain.Member;

public record MemberUpdateResponse(
        boolean success,
        Member member
) {
    public static MemberUpdateResponse of(final boolean success, final Member member) {
        return new MemberUpdateResponse(success, member);
    }
}
