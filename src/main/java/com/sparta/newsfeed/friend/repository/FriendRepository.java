package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
