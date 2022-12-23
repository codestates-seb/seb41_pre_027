package com.codestates.pre027.PreProjectStackOverFlow.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TagDto {
    @Getter
    @AllArgsConstructor
    public static class Response{
        private long tagId;
        private String tagName;
    }

    @Getter
    public static class Post{
        private String tagString;
    }
}
