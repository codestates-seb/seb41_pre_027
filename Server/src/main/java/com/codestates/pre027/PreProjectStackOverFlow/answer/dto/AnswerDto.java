package com.codestates.pre027.PreProjectStackOverFlow.answer.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.lang.Nullable;

public class AnswerDto {

    @Getter
    public static class Post{
        @NotBlank
        private String text;

        @Nullable
        private String imgURL;
    }
    public static class Patch{
        String data;
    }
}
