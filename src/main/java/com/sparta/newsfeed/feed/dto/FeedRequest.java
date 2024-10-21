package com.sparta.newsfeed.feed.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FeedRequest {
    @NotNull(message = "피드가 Null일 수 없습니다.")
    private String content;
}