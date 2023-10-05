package com.jyp.miniboard.member.controller;

import com.jyp.miniboard.member.dto.MemberSaveRequest;
import com.jyp.miniboard.member.dto.MemberSaveResponse;
import com.jyp.miniboard.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public ResponseEntity<MemberSaveResponse> join(@RequestBody @Valid final MemberSaveRequest memberSaveRequest) {
        final MemberSaveResponse memberSaveResponse = memberService.join(memberSaveRequest.name(), memberSaveRequest.email(), memberSaveRequest.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberSaveResponse);
    }
}
