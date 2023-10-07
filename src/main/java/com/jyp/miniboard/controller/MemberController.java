package com.jyp.miniboard.controller;

import com.jyp.miniboard.security.UserAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@UserAuthorize
@RestController
public class MemberController {

    @GetMapping("/api/v1/members")
    public ResponseEntity<String> getMembers() {
        return ResponseEntity.ok("Hello World");
    }
}
