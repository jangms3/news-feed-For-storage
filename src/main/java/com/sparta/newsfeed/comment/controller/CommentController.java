package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feeds/{feedId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 생성 // 토큰 정보를 쿠키에 담아두던지 담아둠, // 그 이후로 서버한테 다른 api 를 요청할 때 마다 토큰을 통해서 유저 id를 가져오는 방법이 있다.  // 쿠키로 유저 아이디를 받는다고 했음 ,,
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto,
                                                            @PathVariable Long feedId,
                                                            @RequestParam Long userId) { // 수정 해야 겠네.. 컨트롤러를 타기전에 미리 토큰을 검증하는 단계를 한다??? 그걸 받아온다??? 가능?
        CommentResponseDto newComment = commentService.createComment(requestDto,feedId, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)  // 201 Created 상태 반환
                .body(newComment);  // 생성된 댓글 반환
    }

    //댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long feedId) {
        List<CommentResponseDto> comments = commentService.getCommentsByFeedId(feedId);
        return ResponseEntity.ok(comments);
    }

    //댓글 수정
    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentRequestDto requestDto,
                                              @PathVariable Long feedId,
                                              @PathVariable Long commentId) {
        commentService.updateComment(requestDto, feedId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long feedId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(feedId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
