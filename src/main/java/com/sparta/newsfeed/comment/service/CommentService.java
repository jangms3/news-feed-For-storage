package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long feedId, Long userId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("feed not found"));//pk 기준으로 찾을꺼면 findById  쓰면 됨.
        Users users = userRepository.findById(userId) //user 테이블이랑 repository랑 맵핑이 되어있음.
                // findByid 는 user의 pk 값이 되는 아이디를 기준으로 찾는 다는 의미.
                .orElseThrow(() -> new IllegalArgumentException("User not found")); //optional 한 객체를 내려준다.

        // 새로운 댓글 생성
        Comment comment = Comment.from(requestDto, feed, users);
        Comment savedComment = commentRepository.save(comment);

        // 저장된 댓글을 CommentResponseDto로 반환
        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public void updateComment(CommentRequestDto requestDto, Long feedId, Long commentId) {
        // Feed와 Comment를 각각 조회
        feedRepository.findById(feedId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Feed user id not found"));

        // 댓글 내용 수정
        comment.updateComment(requestDto.getComment());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long feedId, Long commentId) {
        // Feed와 Comment를 각각 조회
        feedRepository.findById(feedId);
        Comment comment = commentRepository.findCommentById(commentId);

        // 댓글 삭제
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public List<CommentResponseDto> getCommentsByFeedId(Long feedId) {
        // Feed 조회
        Feed feed = feedRepository.findById(feedId);

        // 해당 Feed 에 달린 댓글 목록 조회
        List<Comment> comments = commentRepository.findfeedById(feedId); //comment 아이디 기준으로 찾는거니까 특저지어 줘야 함.
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
