package com.codestates.pre027.PreProjectStackOverFlow.answer.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AnswerDto {

    @Getter
    @AllArgsConstructor
    public static class Response{
        private long questionId;
        private long answerId;
        private long memberId;
        private String memberNick;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private long memberImage;
        private int ratingScore;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotBlank
        private String text;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{
        @Setter
        private long answerId;

        @NotBlank
        private String text;
    }
}
