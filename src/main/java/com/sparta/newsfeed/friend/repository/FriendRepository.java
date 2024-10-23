package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.entity.Friend;
import com.sparta.newsfeed.entity.FriendshipStatus;
import com.sparta.newsfeed.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(value = "select friend from Friend friend WHERE friend.fromUser=:fromUser and friend.status=:status")
    List<Friend> findByFromUserAndStatus(Users fromUser, FriendshipStatus status);
    Optional<Friend> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}

//유저객채를 넘겨주는게 아니고 유저 ID값을 넘겨줄수 잇는걸 알아봐야함

