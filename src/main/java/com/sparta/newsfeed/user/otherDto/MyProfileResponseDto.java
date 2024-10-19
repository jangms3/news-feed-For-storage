package com.sparta.newsfeed.user.otherDto;

import com.sparta.newsfeed.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyProfileResponseDto {
    private String username;
    private String email;
    private String introduction;

    public MyProfileResponseDto(Users user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
    }
}
