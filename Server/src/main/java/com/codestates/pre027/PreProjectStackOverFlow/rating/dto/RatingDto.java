package com.codestates.pre027.PreProjectStackOverFlow.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RatingDto {

    @Getter
    @AllArgsConstructor
    public static class QuestionPost {
        private long questionId;
        private boolean upRating;
        private boolean downRating;
    }

    @Getter
    @AllArgsConstructor
    public static class AnswerPost {

    }

    @Getter
    @AllArgsConstructor
    public static class QuestionPatch {
        private long questionId;
        private int ratingScore;

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }
    }

    public static class AnswernPatch {

    }

    public static class QuestionResponse {

    }

    public static class AnswerResponse {

    }
}
