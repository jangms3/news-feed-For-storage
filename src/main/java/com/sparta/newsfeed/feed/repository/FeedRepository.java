package com.sparta.newsfeed.feed.repository;

import com.sparta.newsfeed.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByIdGreaterThanOrderByIdAsc(Long lastCursorId, Pageable pageable);
}