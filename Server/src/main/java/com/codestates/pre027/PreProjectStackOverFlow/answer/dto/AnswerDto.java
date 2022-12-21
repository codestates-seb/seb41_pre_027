package com.codestates.pre027.PreProjectStackOverFlow.answer.dto;

import com.codestates.pre027.PreProjectStackOverFlow.member.dto.MemberDto;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AnswerDto {

    @Getter
    @AllArgsConstructor
    public static class Response{
        private long questionId;
        private long answerId;
        private MemberDto.Response member;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    public static class Post{
        @NotBlank
        private String text;
    }
    @Getter
    @Setter
    public static class Patch{
        private long answerId;

        @NotBlank
        private String text;
    }
}
