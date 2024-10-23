package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.requestDto.EmailRequestDto;
import com.sparta.newsfeed.user.requestDto.EmailVerifyRequestDto;
import com.sparta.newsfeed.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
        private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequestDto requestDto) { //
        emailService.sendEmail(requestDto.getEmail());
        return ResponseEntity.ok("메일을 확인해주세요");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailVerifyRequestDto verifyRequestDto) {
        boolean isSucceeded = emailService.verifyEmail(verifyRequestDto.getEmail(), verifyRequestDto.getVerificationCode());
        if (!isSucceeded) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("인증 번호가 잘못되었습니다.");
        }
        return ResponseEntity.ok("이메일 인증 성공");
    }
}

