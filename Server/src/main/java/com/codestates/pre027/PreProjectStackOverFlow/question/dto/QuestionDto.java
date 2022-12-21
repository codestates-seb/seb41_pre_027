package com.codestates.pre027.PreProjectStackOverFlow.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class QuestionDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String text;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private long questionId;
        private String title;
        private String text;

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long questionId;
        private String title;
        private String text;
    }
}
