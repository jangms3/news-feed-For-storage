package com.sparta.newsfeed.comment.repository;
import com.sparta.newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findCommentById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
    }
}
