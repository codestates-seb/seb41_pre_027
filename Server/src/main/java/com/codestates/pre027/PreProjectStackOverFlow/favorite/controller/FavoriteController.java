package com.codestates.pre027.PreProjectStackOverFlow.favorite.controller;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.dto.FavoriteDto;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.entity.Favorite;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.mapper.FavoriteMapper;
import com.codestates.pre027.PreProjectStackOverFlow.favorite.service.FavoriteService;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/favorite")
@RequiredArgsConstructor
@Validated
public class FavoriteController {

    private final FavoriteMapper favoriteMapper;
    private final FavoriteService favoriteService;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping("/{member-id}")
    public ResponseEntity postFavorite(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId,
        @RequestBody @Valid FavoriteDto.Post requestBody) {

        Favorite favorite = favoriteMapper.favoritePostDtoToFavorite(requestBody);

        Favorite createdFavorite = favoriteService.createFavorite(favorite,memberId,jwtTokenizer.getMemberId(token));

        FavoriteDto.Response response = favoriteMapper.favoriteToFavoriteResponseDto(createdFavorite);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
