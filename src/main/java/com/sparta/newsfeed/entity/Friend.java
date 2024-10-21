package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

@Entity
@Table(name = "Friends")
@NoArgsConstructor
public class Friend extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users fromUser;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Users toUser;

    private FriendshipStatus status;

    @Builder
    public Friend(Users fromUser, Users toUser, FriendshipStatus status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
    }

    public void acceptFriendRequest() {
        status = FriendshipStatus.ACCEPT;
    }




}
