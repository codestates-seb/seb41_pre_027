package com.codestates.pre027.PreProjectStackOverFlow.favorite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FavoriteDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        private long favorite;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long favorite;

    }
}
