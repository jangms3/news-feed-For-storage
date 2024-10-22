package com.sparta.newsfeed.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FeedResponse {
    private Long id;
    private String content;
//    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}