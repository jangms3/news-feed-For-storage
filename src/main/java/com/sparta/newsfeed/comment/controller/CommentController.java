package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import com.sparta.newsfeed.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/feeds/{feedId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    
    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto,
                                                            @PathVariable Long feedId,
                                                            HttpServletRequest request) {
        Users authenticatedUser = (Users) request.getAttribute("user");

        // 댓글 생성 로직 처리
        CommentResponseDto newComment = commentService.createComment(requestDto, feedId, authenticatedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    // 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long feedId) {
        List<CommentResponseDto> comments = commentService.getCommentsByFeedId(feedId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody CommentRequestDto requestDto,
                                                            @PathVariable Long feedId,
                                                            @PathVariable Long commentId,
                                                            HttpServletRequest request) {
        Users authenticatedUser = (Users) request.getAttribute("user");

        // 댓글 수정 로직 처리
        CommentResponseDto updatedComment = commentService.updateComment(requestDto, feedId, commentId, authenticatedUser.getId());
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long feedId,
                                              @PathVariable Long commentId,
                                              HttpServletRequest request) {
        Users authenticatedUser = (Users) request.getAttribute("user");

        // 댓글 삭제 로직 처리
        commentService.deleteComment(feedId, commentId, authenticatedUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
