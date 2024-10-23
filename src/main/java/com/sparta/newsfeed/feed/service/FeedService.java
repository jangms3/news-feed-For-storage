package com.sparta.newsfeed.feed.service;

import com.sparta.newsfeed.entity.Feed;
import com.sparta.newsfeed.feed.dto.FeedRequest;
import com.sparta.newsfeed.feed.dto.FeedResponse;
import com.sparta.newsfeed.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    @Transactional
    public FeedResponse createFeed(Long userId, FeedRequest requestDto) {
        Feed savedFeed = feedRepository.save(Feed.from(userId, requestDto));

        return savedFeed.to();
    }

    public List<FeedResponse> readAllFeeds() {
        List<Feed> feeds = feedRepository.findAllByOrderByCreatedAtDesc();

        return feeds.stream().map(Feed::to).toList();
    }

    public FeedResponse readFeed(Long id) {
        Feed readFeed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드가 DB에 존재하지 않습니다."));

        return readFeed.to();
    }

    @Transactional
    public FeedResponse updateFeed(Long id, Long userIdFromToken, FeedRequest requestDto) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드가 DB에 존재하지 않습니다."));

        // 해당 피드의 유저 ID와 토큰의 유저 ID 체크
        if (!userIdFromToken.equals(feed.getUser().getId())) {
            throw new IllegalArgumentException("해당 피드의 작성자가 아닙니다.");
        }

        feed.update(requestDto.getContent());

        return feed.to();
    }

    @Transactional
    public void deleteFeed(Long id, Long userIdFromToken) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드가 DB에 존재하지 않습니다."));

        // 해당 피드의 유저 ID와 토큰의 유저 ID 체크
        if (!userIdFromToken.equals(feed.getUser().getId())) {
            throw new IllegalArgumentException("해당 피드의 작성자가 아닙니다.");
        }

        feedRepository.delete(feed);
    }
}