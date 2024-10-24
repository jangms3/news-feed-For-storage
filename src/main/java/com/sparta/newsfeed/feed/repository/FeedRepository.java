package com.sparta.newsfeed.feed.repository;

import com.sparta.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByOrderByCreatedAtDesc();

    @Query("SELECT f FROM Feed f JOIN Friend fr ON fr.toUser.id = f.user.id WHERE fr.fromUser.id = :userId AND fr.status = 'ACCEPT' ORDER BY f.createdAt DESC")
    List<Feed> findAllByUserId(Long userId);
}