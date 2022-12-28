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
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FavoriteController {

    private final FavoriteMapper favoriteMapper;
    private final FavoriteService favoriteService;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping("member/{member-id}/favorite")
    public ResponseEntity postFavorite(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId,
        @Valid @RequestBody FavoriteDto.Post requestBody) {

        System.out.println("###########################맵퍼출발");
        Favorite favorite = favoriteMapper.favoritePostDtoToFavorite(requestBody);

        System.out.println("###########################서비스출발");
        Favorite createdFavorite = favoriteService.createFavorite(favorite,memberId,jwtTokenizer.getMemberId(token));

        System.out.println("###########################맵퍼 출발");
        FavoriteDto.Response response = favoriteMapper.favoriteToFavoriteResponseDto(createdFavorite);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
