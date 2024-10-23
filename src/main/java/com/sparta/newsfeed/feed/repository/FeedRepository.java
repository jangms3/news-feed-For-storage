package com.sparta.newsfeed.feed.repository;

import com.sparta.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByOrderByCreatedAtDesc();

//    @Query()
//    List<Feed>
}