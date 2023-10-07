package com.jyp.miniboard.dto.member;

import com.jyp.miniboard.domain.Member;

public record MemberUpdateResponse(
        boolean success,
        Member member
) {
    public static MemberUpdateResponse of(final boolean success, final Member member) {
        return new MemberUpdateResponse(success, member);
    }
}
