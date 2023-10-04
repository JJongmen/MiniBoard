package com.jyp.miniboard.member;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/v1/members")
    public ResponseEntity<MemberSaveResponse> join(@RequestBody @Valid final MemberSaveRequest memberSaveRequest) {
        final MemberSaveResponse memberSaveResponse = memberService.join(memberSaveRequest.name(), memberSaveRequest.email(), memberSaveRequest.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberSaveResponse);
    }
}
