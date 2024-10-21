package com.sparta.newsfeed.comment.dto;

import com.sparta.newsfeed.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getUsers().getUsername();  // User 엔티티의 username 사용
    }
}
