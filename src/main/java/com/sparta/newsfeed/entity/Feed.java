package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.feed.dto.FeedRequest;
import com.sparta.newsfeed.feed.dto.FeedResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "feeds")
@NoArgsConstructor
public class Feed extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

//    @Column(nullable = false)
//    private Integer likes;

    public Feed(String content) {
//        this.likes = 0;
        this.content = content;
    }

    public static Feed from(FeedRequest requestDto) {
        return new Feed(requestDto.getContent());
    }

    public FeedResponse toEntity() {
        return new FeedResponse(
                getId(),
                getContent(),
//                getLikes(),
                getCreatedAt(),
                getModifiedAt()
        );
    }

    public void update(String content) {
        this.content = content;
    }
}