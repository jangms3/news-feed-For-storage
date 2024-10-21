package com.sparta.newsfeed.feed.repository;

import com.sparta.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}