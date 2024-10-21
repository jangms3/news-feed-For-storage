package com.sparta.newsfeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용은 비워둘 수 없습니다") // 검증 빈 문자열 x
    @Size(min =1, max = 255, message = "댓글 내용은 1자 이상 255자 이하") //댓글 길이 제한
    private String Comment;
}
