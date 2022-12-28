package com.codestates.pre027.PreProjectStackOverFlow.favorite.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.favorite.dto.FavoriteDto;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.entity.Favorite;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    Favorite favoritePostDtoToFavorite(FavoriteDto.Post requestBody);

    FavoriteDto.Response favoriteToFavoriteResponseDto(Favorite favorite);

}
