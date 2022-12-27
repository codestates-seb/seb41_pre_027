package com.codestates.pre027.PreProjectStackOverFlow.comment.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {
    @Getter
    @AllArgsConstructor
    public static class Response{
        private long parentId;
        private long commentId;
        private long memberId;
        private String memberNick;
        private String text;
        private LocalDateTime createdAt;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotBlank
        private String text;
    }
}
