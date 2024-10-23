package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.user.otherDto.MyProfileResponseDto;
import com.sparta.newsfeed.user.otherDto.ProfileResponseDto;
import com.sparta.newsfeed.user.requestDto.LoginRequestDto;
import com.sparta.newsfeed.user.requestDto.SignupRequestDto;
import com.sparta.newsfeed.user.requestDto.UserCheckRequestDto;
import com.sparta.newsfeed.user.requestDto.UserUpdateRequestDto;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail(), requestDto.isRole());
        return ResponseEntity.ok("회원 가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse res) {
        userService.login(loginRequestDto, res);
        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/profile/my")
    @ResponseBody
    public MyProfileResponseDto getMyProfile (HttpServletRequest request) {
        Users user = (Users) request.getAttribute("user");
        return userService.getMyProfile(user.getId());
    }

    @GetMapping("/profile/{userId}")
    @ResponseBody
    public ProfileResponseDto getProfile (@PathVariable Long userId) {
        return userService.getProfile(userId);
    }

    @GetMapping("/check")
    @ResponseBody
    public Boolean checkIdPw (@RequestBody UserCheckRequestDto requestDto) {
        return userService.checkIdPw(requestDto.getEmail(), requestDto.getPassword());
    }

    @PatchMapping("/update/{userId}")
    @ResponseBody
    public MyProfileResponseDto updateUser(@PathVariable Long userId,
                                           @RequestBody @Valid UserUpdateRequestDto requestDto) {
        String username = requestDto.getUsername();
        String introduction = requestDto.getIntroduction();
        userService.updateUser(userId, username, introduction);
        return userService.getMyProfile(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }
}