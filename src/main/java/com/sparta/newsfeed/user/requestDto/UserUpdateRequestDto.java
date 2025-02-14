package com.sparta.newsfeed.user.requestDto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    @Size(min=2, max = 10)
    private String username;
    private String introduction;
}