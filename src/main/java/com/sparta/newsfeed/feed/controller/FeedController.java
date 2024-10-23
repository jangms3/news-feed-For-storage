package com.sparta.newsfeed.feed.controller;

import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.feed.dto.FeedRequest;
import com.sparta.newsfeed.feed.dto.FeedResponse;
import com.sparta.newsfeed.feed.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<FeedResponse> createFeed(
            HttpServletRequest request,
            @RequestBody @Valid FeedRequest requestDto
    ) {
        Users user = (Users) request.getAttribute("user");
//        Long userIdFromToken = user.getId();
        FeedResponse responseDto = feedService.createFeed(user, requestDto);
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
            HttpServletRequest request,
            @PathVariable("feedId") Long id,
            @RequestBody @Valid FeedRequest requestDto
    ) {
        Users user = (Users) request.getAttribute("user");
//        Long userIdFromToken = user.getId();

//        FeedResponse responseDto = feedService.updateFeed(id, userIdFromToken, requestDto);
        FeedResponse responseDto = feedService.updateFeed(id, user, requestDto);
        return ResponseEntity
            .status(HttpStatus.RESET_CONTENT)
                .body(responseDto);
    }

    // DELETE
    @DeleteMapping("api/feeds/{feedId}")
    public ResponseEntity<Void> deleteFeed(
            HttpServletRequest request,
            @PathVariable("feedId") Long id
    ) {
        Users user = (Users) request.getAttribute("user");
        Long userIdFromToken = user.getId();

        feedService.deleteFeed(id, userIdFromToken);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}