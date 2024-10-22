package com.sparta.newsfeed.feed.controller;

import com.sparta.newsfeed.feed.dto.FeedRequest;
import com.sparta.newsfeed.feed.dto.FeedResponse;
import com.sparta.newsfeed.feed.service.FeedService;
import com.sparta.newsfeed.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class FeedController {
    private final FeedService feedService;
    private final JwtUtil jwtUtil;

    // CREATE
    @PostMapping("api/feeds")
    public ResponseEntity<FeedResponse> createFeed(
            @CookieValue(value = "Authorization") String tokenValue,
            @RequestBody @Valid FeedRequest requestDto
    ) {
        Long userId = jwtUtil.getUserIdFromToken(tokenValue);
        FeedResponse responseDto = feedService.createFeed(userId, requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // READ all feeds
    @GetMapping("")
    public ResponseEntity<List<FeedResponse>> readAllFeeds() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(feedService.readAllFeeds());
    }

    // READ selected feed
    @GetMapping("api/feeds/{feedId}")
    public ResponseEntity<FeedResponse> readFeed(@PathVariable("feedId") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(feedService.readFeed(id));
    }

    // UPDATE
    @PutMapping("api/feeds/{feedId}")
    public ResponseEntity<FeedResponse> updateFeed(
            @CookieValue(value = "Authorization") String tokenValue,
            @PathVariable("feedId") Long id,
            @RequestBody @Valid FeedRequest requestDto
    ) {
        Long idFromToken = jwtUtil.getUserIdFromToken(tokenValue);
        FeedResponse responseDto = feedService.updateFeed(id, idFromToken, requestDto);
        return ResponseEntity
            .status(HttpStatus.RESET_CONTENT)
                .body(responseDto);
    }

    // DELETE
    @DeleteMapping("api/feeds/{feedId}")
    public ResponseEntity<Void> deleteFeed(
            @CookieValue(value = "Authorization") String tokenValue,
            @PathVariable("feedId") Long id
    ) {
        Long idFromToken = jwtUtil.getUserIdFromToken(tokenValue);
        feedService.deleteFeed(id, idFromToken);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}