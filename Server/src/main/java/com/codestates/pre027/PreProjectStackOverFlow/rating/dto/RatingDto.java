package com.codestates.pre027.PreProjectStackOverFlow.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RatingDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionResponse {
        private long questionId;
        private long memberId;
        private int ratingScore;
        private boolean upRating;
        private boolean downRating;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerResponse {
        private long answerId;
        private long memberId;
        private int ratingScore;
        private boolean upRating;
        private boolean downRating;
    }

}
