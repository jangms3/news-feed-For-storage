package com.sparta.newsfeed.feed.controller;

import com.sparta.newsfeed.feed.dto.FeedRequest;
import com.sparta.newsfeed.feed.dto.FeedResponse;
import com.sparta.newsfeed.feed.service.FeedService;
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

    // CREATE
    @PostMapping("api/feeds")
    public ResponseEntity<FeedResponse> createFeed(@RequestBody @Valid FeedRequest requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(feedService.createFeed(requestDto));
    }

    // READ all feeds (Home)
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
    public ResponseEntity<FeedResponse> updateFeed(@PathVariable("feedId") Long id, @RequestBody @Valid FeedRequest requestDto) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(feedService.updateFeed(id, requestDto));
    }

    // DELETE
    @DeleteMapping("api/feeds/{feedId}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("feedId") Long id) {
        feedService.deleteFeed(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}