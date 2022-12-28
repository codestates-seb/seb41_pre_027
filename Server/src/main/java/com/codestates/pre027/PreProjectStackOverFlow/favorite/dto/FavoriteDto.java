package com.codestates.pre027.PreProjectStackOverFlow.favorite.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FavoriteDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private long favorite;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long favorite;

    }
}
