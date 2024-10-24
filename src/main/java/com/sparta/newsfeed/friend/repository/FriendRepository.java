package com.sparta.newsfeed.friend.repository;

import com.sparta.newsfeed.entity.Friend;
import com.sparta.newsfeed.entity.FriendshipStatus;
import com.sparta.newsfeed.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(value = "select friend from Friend friend WHERE friend.fromUser=:fromUser and friend.status=:status")
    List<Friend> findByFromUserAndStatusOrderByCreatedAtDesc(Users fromUser, FriendshipStatus status);
    @Query(value = "select friend from Friend friend Where (friend.fromUser = :user or friend.toUser = :user) AND (friend.toUser = :target or friend.fromUser = :target)")
    List<Friend> findFriends(Users user, Users target);

    Optional<Friend> findByFromUserIdAndToUserIdOrderByCreatedAtDesc(Long fromUserId, Long toUserId);
//    void deleteByToUserId(Long friendId);
}


