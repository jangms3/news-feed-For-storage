package com.sparta.newsfeed.user.otherDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private String username;
    private String email;
    private String introduction;

    public ProfileResponseDto(Users user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
    }
}
