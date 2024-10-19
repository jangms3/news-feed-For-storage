package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.user.otherDto.MyProfileResponseDto;
import com.sparta.newsfeed.user.otherDto.ProfileResponseDto;
import com.sparta.newsfeed.user.requestDto.SignupRequestDto;
import com.sparta.newsfeed.user.requestDto.UserCheckRequestDto;
import com.sparta.newsfeed.user.requestDto.UserUpdateRequestDto;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody @Valid SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String pw = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        boolean role = requestDto.isRole();

        userService.signup(username, pw, email, role);
        return ResponseEntity.ok("회원 가입 성공");
    }

    @GetMapping("/profile/my")
    @ResponseBody
    public MyProfileResponseDto getMyProfile (HttpServletRequest request) {
        // 한비님, 필터에서 HttpServletRequest 에 setAttribute 해주세요
        Users user = (Users) request.getAttribute("user");
        Long Userid = user.getId();
        return userService.getMyProfile(Userid);
    }

    @GetMapping("/profile/{userId}")
    @ResponseBody
    public ProfileResponseDto getProfile (@PathVariable Long userId) {
        return userService.getProfile(userId);
    }

    @GetMapping("/check")
    @ResponseBody
    public Boolean checkIdPw (@RequestBody UserCheckRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        return userService.checkIdPw(email, password);
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