package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(nullable = false)
    private String comment;

    public static Comment from(CommentRequestDto requestDto, Feed feed, Users users) {
        Comment comment = new Comment();
        comment.initData(requestDto, feed, users);
        return comment;
    }

    public void initData(CommentRequestDto requestDto, Feed feed, Users users) {
        this.feed = feed;
        this.users = users;
        this.comment = requestDto.getComment();
    }

    // 댓글 데이터 수정
    public void updateComment(String updatedComment) {
        this.comment = updatedComment;
    }
}
