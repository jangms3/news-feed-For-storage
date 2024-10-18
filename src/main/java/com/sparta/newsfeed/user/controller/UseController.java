package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("//user")
public class UseController {
    private final UserService userService;
    @PostMapping("/signup")
    public String signup (@RequestBody SignupRequestDto requestDto) {
        return
    }
}
