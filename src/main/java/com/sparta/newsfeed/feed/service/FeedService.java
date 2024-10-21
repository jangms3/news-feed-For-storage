package com.sparta.newsfeed.feed.service;

import com.sparta.newsfeed.entity.Feed;
import com.sparta.newsfeed.feed.dto.FeedRequest;
import com.sparta.newsfeed.feed.dto.FeedResponse;
import com.sparta.newsfeed.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    @Transactional
    public FeedResponse createFeed(FeedRequest requestDto) {
        // RequestDTO -> Entity
        // DB 저장
        Feed savedFeed = feedRepository.save(Feed.from(requestDto));

        // Entity -> ResponseDTO
        return savedFeed.toEntity();
    }

    public List<FeedResponse> readNextFeeds(Long lastCursorId, Integer pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        List<Feed> feeds = feedRepository.findByIdGreaterThanOrderByIdAsc(lastCursorId, pageable);
        return feeds.stream()
                .map(Feed::toEntity)
                .collect(Collectors.toList());
    }

    public FeedResponse readFeed(Long id) {
        // 해당 피드가 DB에 존재하는지 확인
        Feed readFeed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드가 DB에 존재하지 않습니다."));

        // Entity -> ResponseDTO
        return readFeed.toEntity();
    }

    @Transactional
    public FeedResponse updateFeed(Long id, FeedRequest requestDto) {
        // 해당 피드가 DB에 존재하는지 확인
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드가 DB에 존재하지 않습니다."));

        // RequestDTO -> Entity
        feed.update(requestDto.getContent());

        // Entity -> ResponseDTO
        return feed.toEntity(); // DTO 반환할 경우
    }

    @Transactional
    public void deleteFeed(Long id) {
        // 해당 피드가 DB에 존재하는지 확인
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드가 DB에 존재하지 않습니다."));

        feedRepository.delete(feed);
    }
}