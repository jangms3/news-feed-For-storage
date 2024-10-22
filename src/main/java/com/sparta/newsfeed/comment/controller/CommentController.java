import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;  // JwtUtil 주입

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto,
                                                            @PathVariable Long feedId,
                                                            HttpServletRequest request) {
        // 쿠키에서 JWT 토큰 추출
        String token = getJwtFromCookies(request);

        // 토큰이 없거나 유효하지 않으면 401 Unauthorized 반환
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 인증 실패 시
        }

        // 토큰에서 사용자 ID 추출
        Long userId = jwtUtil.getUserIdFromToken(token);

        // 댓글 생성 로직 처리
        CommentResponseDto newComment = commentService.createComment(requestDto, feedId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
       // 생성된 댓글 반환
    }

    // JWT 토큰을 쿠키에서 추출하는 메서드
    private String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;  // 쿠키에서 토큰이 없으면 null 반환
    }

    // 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long feedId) {
        List<CommentResponseDto> comments = commentService.getCommentsByFeedId(feedId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody CommentRequestDto requestDto,
                                              @PathVariable Long feedId,
                                              @PathVariable Long commentId) {
        commentService.updateComment(requestDto, feedId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long feedId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(feedId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
