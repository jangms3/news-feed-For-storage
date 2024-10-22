package com.sparta.newsfeed.friend.dto;

import com.sparta.newsfeed.entity.Friend;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FriendResponseDto {
    private String username;

    public static FriendResponseDto to(Friend friend) {
        return FriendResponseDto.builder()
                .username(friend.getToUser().getUsername())
                .build();
    }
}



