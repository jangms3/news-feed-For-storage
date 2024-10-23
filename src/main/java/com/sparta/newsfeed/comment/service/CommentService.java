package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.Feed;
import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.feed.repository.FeedRepository;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    // 영속성 전이 강의 한번 더 들어봐야 겠다.
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long feedId, Long userId) {
        // Feed 조회
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found"));

        // User 조회
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 새로운 댓글 생성
        Comment comment = Comment.from(requestDto, feed, users);
        Comment savedComment = commentRepository.save(comment);

        // 저장된 댓글을 CommentResponseDto로 반환
        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long feedId, Long commentId, Long userId) {
        // Feed와 Comment를 각각 조회
        Comment comment = commentRepository.findCommentById(commentId);
        Feed feed = comment.getFeed();

        // 권한 확인 (댓글 작성자와 수정하려는 사용자가 동일한지 확인)
        if (!comment.getUsers().getId().equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to update this comment");
        }

        // 댓글 내용 수정
        comment.updateComment(requestDto.getComment());

        return null;
    }

    @Transactional
    public void deleteComment(Long feedId, Long commentId, Long userId) {
        // Feed와 Comment를 각각 조회
        Comment comment = commentRepository.findCommentById(commentId);
        Feed feed = comment.getFeed();

        // 권한 확인 (댓글 작성자와 삭제하려는 사용자가 동일한지 확인)
        if (!comment.getUsers().getId().equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to delete this comment");
        }

        // 댓글 삭제
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public List<CommentResponseDto> getCommentsByFeedId(Long feedId) {
        // Feed 조회
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found"));

        // 해당 Feed에 달린 댓글 목록 조회
        List<Comment> comments = commentRepository.findByFeed(feed);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
